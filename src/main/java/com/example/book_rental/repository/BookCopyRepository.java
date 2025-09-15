package com.example.book_rental.repository;

import com.example.book_rental.entity.BookCopy;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM BookCopy c WHERE c.copyId = :copyId")
    Optional<BookCopy> findByIdForUpdate(@Param("copyId") Long copyId);

    @Query("SELECT c FROM BookCopy c WHERE c.book.isbn = :isbn AND c.available = true")
    List<BookCopy> findAvailableByIsbn(@Param("isbn") String isbn);
}

