package br.com.involves.poe.service;

import br.com.involves.poe.utils.CreateHashId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by marcelo on 20/09/16.
 */
public abstract class AbstractHashService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHashService.class);

    protected final String createHashId(String client, String hash, String endPoint) {

        CreateHashId hashId = new CreateHashId(client, hash, endPoint);

        LOGGER.debug("Id create: " + hashId.toString());

        return hashId.toString();
    }

}
