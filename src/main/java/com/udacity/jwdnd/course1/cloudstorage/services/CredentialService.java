package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final HashService hashService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.hashService = hashService;
    }

    public void encryptPassword(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setKey(encodedKey);
        String encodedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encodedPassword);
    }


    public Integer createCredential(Credential credential) {
        return credentialMapper.insert(credential);
    }

    public List<Credential> getAllByUserId(Integer userId) {
        return credentialMapper.getAllByUserId(userId);
    }

    public Credential getById(Integer credentialId) {
        return credentialMapper.getById(credentialId);
    }

    public Integer deleteById(Integer credentialId) {
        return credentialMapper.deleteById(credentialId);
    }

    public Integer update(Credential credential) {
        return credentialMapper.update(credential);
    }

    public void updateCredentialWithKey(Credential credential) {
        credential.setKey(credentialMapper
                .getById(credential.getCredentialId())
                .getKey());
    }

}
