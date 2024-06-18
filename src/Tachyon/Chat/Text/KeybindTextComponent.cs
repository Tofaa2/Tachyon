using Tachyon.Util;

namespace Tachyon.Chat.Text;


public interface IKeybindComponent : IComponent
{
        
        public IKeybindComponent Keybind(string keybind);
        public IKeybindComponent Keybind(ClientKeybind keybind);

}

internal sealed class KeybindTextComponent : AbstractComponent, IKeybindComponent
{
        public KeybindTextComponent(string keybind)
        {
                _json["keybind"] = keybind;
        }
        
        public KeybindTextComponent(ClientKeybind keybind) : this(keybind.Key)
        {
        }
        
        public IKeybindComponent Keybind(string keybind)
        {
                _json["keybind"] = keybind;
                return this;
        }

        public IKeybindComponent Keybind(ClientKeybind keybind)
        {
                return Keybind(keybind.Key);
        }
}