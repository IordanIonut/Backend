package com.example.MUsicPLay.Repository;

import com.example.MUsicPLay.Model.PlaylistContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistContentRepository extends JpaRepository<PlaylistContent,Long> {

}