package com.ssafy.FFP.Controller;

import com.ssafy.FFP.Dto.SearchDto;
import com.ssafy.FFP.Dto.ShelnimalDto;
import com.ssafy.FFP.Dto.TokenDto;
import com.ssafy.FFP.Service.ShelnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5500", "https://j6e105.p.ssafy.io"}, allowCredentials = "true", allowedHeaders = "*", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.HEAD,
        RequestMethod.OPTIONS })
@RestController
public class ShelnimalController {

    @Autowired
    ShelnimalService shelnimalService;

    // 특정 공고 조회
    @GetMapping("/shel/{no}")
    public ResponseEntity<?> select(@PathVariable String no){
        int shelNo = Integer.parseInt(no);
        ShelnimalDto shelnimalDto = shelnimalService.select(shelNo);

        if(shelnimalDto != null) {
            return ResponseEntity.ok().body(shelnimalDto);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "오류 발생.");
        }
    }

    // 공고일 종료일이 최소 오늘인 공고 목록 조회
    @GetMapping("/shel")
    public ResponseEntity<?> list(){
        LocalDate seoulNow = LocalDate.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = seoulNow.format(formatter);
        int sdt = Integer.parseInt(formatedNow);
        List<ShelnimalDto> shelnimalDtos = shelnimalService.list(sdt);

        if(shelnimalDtos != null) {
            return ResponseEntity.ok().body(shelnimalDtos);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "오류 발생.");
        }
    }

    // 유기 동물 제안
    @GetMapping("/shel/match/{no}")
    public ResponseEntity<?> match(@PathVariable String no){
        int userNo = Integer.parseInt(no);
        List<ShelnimalDto> shelnimalDtos = shelnimalService.match(userNo);

        if(shelnimalDtos != null) {
            return ResponseEntity.ok().body(shelnimalDtos);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "오류 발생.");
        }
    }

    // 검색
    @PostMapping("/shel")
    public ResponseEntity<?> find(@RequestBody SearchDto searchDto){

        List<ShelnimalDto> shelnimalDtos = shelnimalService.find(searchDto);

        if(shelnimalDtos != null) {
            return ResponseEntity.ok().body(shelnimalDtos);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "오류 발생.");
        }
    }
}