package com.stocktonner.backend.repositories;

import com.stocktonner.backend.entities.Tonner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TonnerRepository extends JpaRepository<Tonner, Long> {

    @Query(nativeQuery = true, value =
            "SELECT obj.* FROM tb_tonner obj " +
                    "WHERE (:model = '' OR UPPER(obj.model) LIKE UPPER(CONCAT('%', :model, '%'))) " +
                    "AND (:status = '' OR CAST(obj.status AS VARCHAR) = :status)")
    List<Tonner> searchByModel(String model, String status);

    /*Em alguns casos onde eu precise buscar somente algumas colunas da tabela e uso o "AS" é interessante eu
    * usar projection. Mas nesse caso eu estou usando "obj.*, isto é, buscando todas as colunas da tabela. Logo
    * somente com DTO não teremos problemas de mapeamento com o nativeQuery.*/



    @Query(nativeQuery = true, value = "SELECT SUM(stock_current) FROM tb_tonner")
    Integer searchSumCurrentAll();

    /* Veja que nesse caso eu não posso usar "<Tonner>". Isso porque só se eu quisesse mostrar por exemplo todos
       as colunas da tabelas ou apenas uma coluna, mas desde que fosse apenas uma consulta. Nesse caso não posso
       colcoar "<Tonner>" porque eu quero retornar a soma dos registros apenas, e por isso vai dar erro no mapeamento.

       Outro detalhe é que nesse caso eu não preciso usar o "Optional<Tonner>", porque eu não terei valor null, onde os
       valores só podem ser 0 ou valores acima. Nunca vai da null.
    * */

    @Query(nativeQuery = true, value = "SELECT SUM(request) FROM tb_tonner")
    Integer searchSumRequest();
}
