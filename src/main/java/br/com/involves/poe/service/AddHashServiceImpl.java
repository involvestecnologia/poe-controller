package br.com.involves.poe.service;

import br.com.involves.poe.exception.HashExistingException;
import br.com.involves.poe.repository.PoeHashRepository;
import br.com.involves.poe.table.PoeHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@EnableScan
@Service
public class AddHashServiceImpl implements AddHashService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddHashServiceImpl.class);

    @Autowired
    private PoeHashRepository repository;

    @Autowired
    private FindHashService findHashService;

    @Override
    public void add(String client, String hash, String json, String endPoint) throws HashExistingException {

        valid(client, hash, endPoint);

        if (findHashService.exists(client, hash, endPoint)) {
            LOGGER.debug("Hash encontrada. hash/clint: " + hash + "/" + client);
            throw new HashExistingException();
        }

        LOGGER.debug("Gravando registro com a hash/clint: " + hash + "/" + client);

        PoeHash table = new PoeHash(client, hash, json, endPoint);
        repository.save(table);
    }

    private void valid(String client, String hash, String endPoint) {

        if (StringUtils.isEmpty(client) ||
                StringUtils.isEmpty(hash) ||
                StringUtils.isEmpty(endPoint)) {

            LOGGER.error("Erro: Um ou mais argumentos no foram enviados.");

            throw new IllegalArgumentException("Todos os parametros so obrigatrios.");
        }

    }


}
