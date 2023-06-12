package com.example.binanceanalizator.Controllers.Rest;

import com.example.binanceanalizator.Models.SMA;
import com.example.binanceanalizator.Models.SMARequestBody;
import com.example.binanceanalizator.Services.MovingAverageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/stats")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class StatsRestController {

   public static String format= "yyyy-MM-dd HH:mm:ss";

   public static String FETCH_SMA="/api/stats/SMA";

    MovingAverageService movingAverageService;
    @GetMapping
    public String def(){
    return "pattern is yyyy-MM-dd HH:mm:ss";
    }
    @GetMapping("/SMA")
    public ResponseEntity<List<SMA>> getSMAForAPeriod(@Valid @RequestBody SMARequestBody smaRequestBody) throws ParseException {

//        if(bindingResult.hasErrors()){
//            List<ObjectError> errors=bindingResult.getAllErrors();
//            HttpHeaders httpHeaders=new HttpHeaders();
//            errors.forEach(error->httpHeaders.add(error.getObjectName(), String.valueOf(error)));
//            return ResponseEntity.badRequest()
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .headers(httpHeaders)
//                    .body(null);
//        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);

    long start = simpleDateFormat.parse(smaRequestBody.getStartTime()).getTime();

    long end = simpleDateFormat.parse(smaRequestBody.getEndTime()).getTime();


        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("stats_name","SMA")
                .body(movingAverageService.calculateSMAForAPeriod(smaRequestBody.getSymbol(),smaRequestBody.getInterval(),start,end));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public Map<String,String> handleParseExceptions(ParseException e){
    Map map = new HashMap<String,String>();
    map.put("parseError",e.getMessage());
    return map;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String ,String> handleValidationExceptions(
            MethodArgumentNotValidException e){
        Map<String,String> errors=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error->{
            String fieldName= ((FieldError) error).getField();
            String errorMessage=error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return errors;
    }
}
