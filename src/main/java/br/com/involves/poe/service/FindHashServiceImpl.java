package br.com.involves.poe.service;

import br.com.involves.poe.exception.HashNotFindException;
import br.com.involves.poe.repository.DuplicateEndPointRepository;
import br.com.involves.poe.table.DuplicateEndPointTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@EnableScan
@Service
public class FindHashServiceImpl implements FindHashService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindHashServiceImpl.class);

    @Autowired
    private DuplicateEndPointRepository repository;

    @Override
    public boolean exists(String client, String hash, String endPoint) {

        LOGGER.debug("Procurando o registro com a hash/clint: " + hash + "/" + client);

        try {
            repository.findByClientAndHashAndEndPoint(client, hash, endPoint);
            return true;
        }catch (EmptyResultDataAccessException ex){
            return false;
        }
    }

    @Override
    public String find(String client, String hash, String endPoint) throws HashNotFindException {
        LOGGER.debug("Procurando o registro com a hash/clint: " + hash + "/" + client);

        try {
            DuplicateEndPointTable item = repository.findByClientAndHashAndEndPoint(client, hash, endPoint);
            return item.getJson();
        }catch (EmptyResultDataAccessException ex){
            throw new HashNotFindException("Hash not find Client: {0}, Hash: {1}, EndPoint: {2}", client, hash, endPoint);
        }

    }

}
