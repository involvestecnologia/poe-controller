package br.com.involves.poe.service;

import br.com.involves.poe.exception.HashExistingException;

public interface AddHashService {
    void add(String client, String hash, String json, String endPoint) throws HashExistingException;
}
