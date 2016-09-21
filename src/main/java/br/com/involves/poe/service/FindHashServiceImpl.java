package br.com.involves.poe.service;

import br.com.involves.poe.exception.HashNotFindException;
import br.com.involves.poe.repository.PoeHashRepository;
import br.com.involves.poe.table.PoeHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@EnableScan
@Service
public class FindHashServiceImpl extends AbstractHashService implements FindHashService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindHashServiceImpl.class);

    @Autowired
    private PoeHashRepository repository;

    @Override
    public boolean exists(String client, String hash, String endPoint) {
        LOGGER.debug("Procurando o registro com a hash/clint: " + hash + "/" + client);

        String id = createHashId(client, hash, endPoint);
        return repository.exists(id);
    }

    @Override
    public String find(String client, String hash, String endPoint) throws HashNotFindException {
        LOGGER.debug("Procurando o registro com a hash/clint: " + hash + "/" + client);

        String id = createHashId(client, hash, endPoint);

        PoeHash item = repository.findOne(id);

        if( item == null ){
            throw new HashNotFindException("Hash not find Client: {0}, Hash: {1}, EndPoint: {2}", client, hash, endPoint);
        }

        return item.getJson();
    }

}
