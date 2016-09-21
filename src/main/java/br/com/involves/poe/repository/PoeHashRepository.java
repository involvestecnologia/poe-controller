package br.com.involves.poe.repository;

import br.com.involves.poe.table.PoeHash;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PoeHashRepository extends CrudRepository<PoeHash, String> {

}