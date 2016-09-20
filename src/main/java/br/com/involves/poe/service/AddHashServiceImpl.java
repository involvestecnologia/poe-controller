package br.com.involves.poe.service;

import br.com.involves.poe.exception.HashExistingException;
import br.com.involves.poe.repository.DuplicateEndPointRepository;
import br.com.involves.poe.table.DuplicateEndPointTable;
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
    private DuplicateEndPointRepository repository;

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

        DuplicateEndPointTable table = new DuplicateEndPointTable(client, hash, json, endPoint);
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
