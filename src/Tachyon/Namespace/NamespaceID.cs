using System.Text.RegularExpressions;

namespace Tachyon.Namespace;

/**
 *     Namespace: [a-z0-9.-_]
    Value: [a-z0-9.-_/]
 */
public class NamespaceId
{
    
    private static readonly Regex NamespaceRegex = new(@"^([a-z0-9.-_]+)([a-z0-9.-_/]+)$", RegexOptions.Compiled);
    private static readonly Regex DomainRegex = new(@"^([a-z0-9.-_]+)$", RegexOptions.Compiled);

    public static NamespaceId FromString(string str)
    {
        var split = str.Split(':');
        if (split.Length != 2)
        {
            throw new NamespaceException("Invalid namespace string");
        }
        return new NamespaceId(split[0], split[1]);
    }
    
    public static NamespaceId FromString(string domain, string path)
    {
        return new NamespaceId(domain, path);
    }
    
    public static NamespaceId Minecraft(string path)
    {
        return new NamespaceId("minecraft", path);
    }

    public readonly string Full;
    public readonly string Domain, Path;

    private NamespaceId(string domain, string path)
    {
        if (!DomainRegex.IsMatch(domain))
        {
            throw new NamespaceException("Invalid domain");
        }
        if (!NamespaceRegex.IsMatch(path))
        {
            throw new NamespaceException("Invalid path");
        }
        Domain = domain;
        Path = path;
        Full = $"{domain}:{path}";
    }
    

}