package pl.edu.pwsztar.shapewars.services;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Changelog;
import pl.edu.pwsztar.shapewars.repositories.ChangelogRepository;

@Service
public class ChangelogService {

    @Autowired
    private ChangelogRepository changelogRepository;


}
