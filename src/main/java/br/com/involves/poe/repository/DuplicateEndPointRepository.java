package br.com.involves.poe.repository;

import br.com.involves.poe.table.DuplicateEndPointTable;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface DuplicateEndPointRepository extends CrudRepository<DuplicateEndPointTable, String> {

    DuplicateEndPointTable findByClientAndHashAndEndPoint(String client, String hash, String endPoint);

}