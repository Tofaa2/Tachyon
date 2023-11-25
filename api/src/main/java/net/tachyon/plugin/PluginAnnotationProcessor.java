package net.tachyon.plugin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.tachyon.utils.validate.Check;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("net.tachyon.plugin.PluginData")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public final class PluginAnnotationProcessor extends AbstractProcessor {

    private boolean hasMainBeenFound = false;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) return false;

        Element mainPluginElement;
        hasMainBeenFound = false;
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(PluginData.class);
        Check.stateCondition(elements.size() > 1, "Multiple of the same plugins instance were found.");
        if (elements.isEmpty()) {
            return false;
        }
        if (hasMainBeenFound) {
            raiseError("Multiple main plugins were found. Aborting.");
            return false;
        }
        mainPluginElement = elements.iterator().next();
        hasMainBeenFound = true;

        TypeElement mainPluginType;
        if (mainPluginElement instanceof TypeElement typeElement) {
            mainPluginType = typeElement;
        }
        else {
            raiseError("Main plugin element is not a type element. Aborting.");
            return false;
        }

        if (!(mainPluginType.getEnclosingElement() instanceof PackageElement) ) {
            raiseError( "Main plugin class is not a top-level class", mainPluginType );
            return false;
        }

        if (mainPluginType.getModifiers().contains( Modifier.STATIC ) ) {
            raiseError( "Main plugin class cannot be static nested", mainPluginType );
            return false;
        }

        if ( mainPluginType.getModifiers().contains( Modifier.ABSTRACT ) ) {
            raiseError( "Main plugin class cannot be abstract", mainPluginType );
            return false;
        }

        // Checking for the no-args constructor
        checkForNoArgsConstructor(mainPluginType);
        JsonObject pluginJson = new JsonObject();
        PluginData pluginAnnotation = mainPluginType.getAnnotation(PluginData.class);
        Check.notNull(pluginAnnotation, "pluginAnnotation");

        pluginJson.addProperty("entry", mainPluginType.getQualifiedName().toString());
        pluginJson.addProperty("name", pluginAnnotation.name());
        pluginJson.addProperty("version", pluginAnnotation.version());
        pluginJson.addProperty("description", pluginAnnotation.description());
        JsonArray authors = new JsonArray();
        for (String author : pluginAnnotation.authors()) {
            authors.add(author);
        }
        pluginJson.add("authors", authors);
        JsonArray dependencies = new JsonArray();
        for (String dependency : pluginAnnotation.dependencies()) {
            dependencies.add(dependency);
        }
        pluginJson.add("dependencies", dependencies);

        // Write the tachyon-plugin.json
        try {
            FileObject fo = this.processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", "plugin.json");
            Writer writer = fo.openWriter();
            writer.write(pluginJson.toString());
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private void checkForNoArgsConstructor(TypeElement mainPluginType) {
        for ( ExecutableElement constructor : ElementFilter.constructorsIn( mainPluginType.getEnclosedElements() ) ) {
            if ( constructor.getParameters().isEmpty() ) {
                return;
            }
        }
        raiseError( "Main plugin class must have a no argument constructor.", mainPluginType );
    }

    private void raiseError(String message) {
        this.processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, message );
    }

    private void raiseError(String message, Element element) {
        this.processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, message, element );
    }

    private TypeMirror fromClass(Class<?> clazz) {
        return processingEnv.getElementUtils().getTypeElement( clazz.getName() ).asType();
    }

}
