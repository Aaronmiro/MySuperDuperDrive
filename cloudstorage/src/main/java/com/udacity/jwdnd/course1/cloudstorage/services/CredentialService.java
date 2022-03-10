package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Aaron
 * @date 1/14/22 1:52 PM
 */
@Service
public class CredentialService {
    @Autowired
    private CredentialMapper credentialMapper;
    @Autowired
    private EncryptionService encryptionService;

    public List<Credential> getCredentialsByUserId(Integer userId) {
        return credentialMapper.getCredentialsByUserId(userId);
    }

    public int insertCredential(Credential credential) {
        Integer credentialId = credential.getCredentialId();
        if (credentialId != null) {
            Credential c = credentialMapper.getOneById(credentialId).get(0);
            String encodedKey = c.getKey();
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
            credential.setPassword(encryptedPassword);
            credential.setKey(encodedKey);
            return credentialMapper.updateCredential(credential);
        } else {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
            credential.setKey(encodedKey);
            credential.setPassword(encryptedPassword);
            return credentialMapper.insertCredential(credential);
        }
    }

    public boolean deleteCredentialById(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

    public List<CredentialForm> decryptPassword(List<Credential> credentials){
        List<CredentialForm> credentialList = new ArrayList<>();
        for (Credential credential : credentials) {
            CredentialForm credentialForm = new CredentialForm();
            credentialForm.setCredentialId(credential.getCredentialId());
            credentialForm.setUrl(credential.getUrl());
            credentialForm.setUsername(credential.getUsername());
            credentialForm.setPassword(credential.getPassword());
            String decryptValue = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credentialForm.setPasswordDec(decryptValue);
            credentialForm.setKey(credential.getKey());
            credentialForm.setUserId(credential.getUserId());
            credentialList.add(credentialForm);
        }
        return credentialList;
    }
}
