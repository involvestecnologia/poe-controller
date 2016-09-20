package br.com.involves.poe.service;

import br.com.involves.poe.exception.HashNotFindException;

public interface FindHashService {

    /**
     *
     * Método vai verificar se o registro já existe.
     *
     * @param client
     * @param hash
     * @param endPoint
     * @return true/false
     */
    boolean exists(String client, String hash, String endPoint);

    /**
     * Método vai consultar o banco em busca do objeto e retornar o json.
     *
     * @param client
     * @param hash
     * @param endPoint
     * @return
     */
    String find(String client, String hash, String endPoint) throws HashNotFindException;
}
