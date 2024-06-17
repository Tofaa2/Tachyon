using System.Security.Cryptography;
using Org.BouncyCastle.Crypto;
using Org.BouncyCastle.Crypto.Encodings;
using Org.BouncyCastle.Crypto.Engines;
using Org.BouncyCastle.Crypto.Generators;
using Org.BouncyCastle.Security;
using Org.BouncyCastle.X509;

namespace Tachyon.Network.Packet;

public sealed class PacketCryptography
{

    private RsaKeyPairGenerator? _provider;
    
    private IAsymmetricBlockCipher _encryptCipher;
    private IAsymmetricBlockCipher _decryptCipher;

    internal byte[] _verifyToken;
    internal byte[] _publicKey;
    internal AsymmetricCipherKeyPair _keyPair;

    public AsymmetricCipherKeyPair GenerateKeyPair()
    {
        if (_provider is null)
        {
            _provider = new RsaKeyPairGenerator();
            _provider.Init(new KeyGenerationParameters(new SecureRandom(), 1024));
            _encryptCipher = new Pkcs1Encoding(new RsaEngine());
            _decryptCipher = new Pkcs1Encoding(new RsaEngine());
            _keyPair = _provider.GenerateKeyPair();

            _encryptCipher.Init(true, _keyPair.Public);
            _decryptCipher.Init(false, _keyPair.Private);   
        }
        return _keyPair;
    }
    
    public byte[] Decrypt(byte[] toDecrypt) => _decryptCipher.ProcessBlock(toDecrypt, 0, _decryptCipher.GetInputBlockSize());

    public byte[] Encrypt(byte[] toDecrypt) => _encryptCipher.ProcessBlock(toDecrypt, 0, _encryptCipher.GetInputBlockSize());

    public (byte[] publicKey, byte[] randomToken) GeneratePublicKeyAndToken()
    {
        var randomToken = RandomNumberGenerator.GetBytes(4);

        _verifyToken = randomToken;
        _publicKey = SubjectPublicKeyInfoFactory.CreateSubjectPublicKeyInfo(_keyPair.Public).ToAsn1Object().GetDerEncoded();

        return (_publicKey, _verifyToken);
    }

}