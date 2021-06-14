package com.aweshop.products.upload.watch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchRepository extends JpaRepository<WatchEntity, Long> {

}
