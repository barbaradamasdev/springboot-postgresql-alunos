package com.bddev.springboot.controllers;

import com.bddev.springboot.dtos.AlunoRecordDto;
import com.bddev.springboot.models.AlunoModel;
import com.bddev.springboot.repositories.AlunoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import jakarta.validation.Valid;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    AlunoRepository alunoRepository;

    @PostMapping("/alunos")
    @Operation(
            description = "Create Post service",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully created post!",
                            content = @Content(
                                    mediaType ="application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Successfully created post!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<AlunoModel> saveAluno(@RequestBody @Valid AlunoRecordDto alunoRecordDto) {
        var alunoModel = new AlunoModel();
        BeanUtils.copyProperties(alunoRecordDto, alunoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoRepository.save(alunoModel));
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoModel>> getAllAluno(){
        return ResponseEntity.status(HttpStatus.OK).body(alunoRepository.findAll());
    }

    @GetMapping("/alunos/{id}")
    public ResponseEntity<Object> getOneAlunos(@PathVariable(value = "id")UUID id) {
        Optional<AlunoModel> aluno0 = alunoRepository.findById(id);
        if(aluno0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno nao encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(aluno0.get());
    }

    @PutMapping("/alunos/{id}")
    public ResponseEntity<Object> updateAluno(@PathVariable(value = "id")UUID id,
                                              @RequestBody @Valid AlunoRecordDto alunoRecordDto) {
        Optional<AlunoModel> aluno0 = alunoRepository.findById(id);
        if(aluno0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno nao encontrado.");
        }
        var alunoModel = aluno0.get();
        BeanUtils.copyProperties(alunoRecordDto, aluno0);
        return ResponseEntity.status(HttpStatus.OK).body(alunoRepository.save(alunoModel));
    }

    @DeleteMapping("/alunos/{id}")
    public ResponseEntity<Object> deleteAluno(@PathVariable(value = "id")UUID id) {
        Optional<AlunoModel> aluno0 = alunoRepository.findById(id);
        if(aluno0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno nao encontrado.");
        }
        alunoRepository.delete(aluno0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Aluno deletado com sucesso");
    }

}
