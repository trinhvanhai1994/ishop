package com.ominext.cms.repository;

import com.ominext.cms.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findFirstById(Long id);

    @Query(value="select * from product where id in(:ids)", nativeQuery = true)
    List<Product> findAllByIds(@Param("ids") List<Long> ids);
}
