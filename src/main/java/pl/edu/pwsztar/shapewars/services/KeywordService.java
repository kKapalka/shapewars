package pl.edu.pwsztar.shapewars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwsztar.shapewars.entities.Keyword;
import pl.edu.pwsztar.shapewars.entities.dto.KeywordDto;
import pl.edu.pwsztar.shapewars.repositories.KeywordRepository;

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    public KeywordDto save(KeywordDto dto){
        Keyword keyword = updateKeyword(dto);
        return KeywordDto.fromEntity(keywordRepository.save(keyword));
    }
    private Keyword updateKeyword(KeywordDto dto){
        Keyword keyword = new Keyword();
        keyword.setName(dto.getName());
        keyword.setExplanation(dto.getDescription());
        return keyword;
    }
}
