package com.maybach7.formhandler.service;

import com.maybach7.formhandler.dao.CredentialsDao;
import com.maybach7.formhandler.entity.Credentials;

public class CredentialsService {

    private final static CredentialsService INSTANCE = new CredentialsService();
    private static final CredentialsDao credentialsDao = CredentialsDao.getInstance();

    public Credentials createCredentials(Credentials credentials) {
        return credentialsDao.save(credentials);
    }

    public static CredentialsService getInstance() {
        return INSTANCE;
    }
}