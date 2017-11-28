package com.jaspersoft.jasperserver.api.engine.jasperreports.service.impl.azuresql;

import java.security.KeyStore;

public class AzureManagementCredentials {
    private String subscriptionId;
    private byte[] keyStoreBytes;
    private char[] keyStorePassword;
    private String keyStoreType = KeyStore.getDefaultType();

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public byte[] getKeyStoreBytes() {
        return keyStoreBytes;
    }

    public void setKeyStoreBytes(byte[] keyStoreBytes) {
        this.keyStoreBytes = keyStoreBytes;
    }

    public char[] getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(char[] keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyStoreType() {
        return keyStoreType;
    }

    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

}
