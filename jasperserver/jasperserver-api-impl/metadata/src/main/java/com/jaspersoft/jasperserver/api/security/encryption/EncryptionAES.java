package com.jaspersoft.jasperserver.api.security.encryption;

import java.security.KeyPair;
import java.security.PrivateKey;

/**
 * The latest version of jcryption.js uses AES encryption, but JCryption for Java
 * has not kept up with the AES technique to date.  I suspect they might so this is a place holder
 * for that.  DO NOT USE at this time!
 *
 * @author norm
 * @since 2/9/2012
 */
public class EncryptionAES implements Encryption {
    /**
     * {@inheritDoc}
     */
    public KeyPair generateKeypair(int keyLength) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * {@inheritDoc}
     */
    public String decrypt(String encrypted, PrivateKey privateKey) {
        throw new RuntimeException("Not implemented");
    }
}
