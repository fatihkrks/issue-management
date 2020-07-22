package com.temelt.issuemanagement.api;


import com.temelt.issuemanagement.dto.IssueDto;
import com.temelt.issuemanagement.service.impl.IssueServiceImpl;
import com.temelt.issuemanagement.util.ApiPaths;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by temelt on 4.02.2019.
 */
@RestController
@RequestMapping(ApiPaths.IssueCtrl.CTRL)
@Api(value = ApiPaths.IssueCtrl.CTRL,description = "Issue APIs")
public class IssueController {

    private final IssueServiceImpl issueServiceImpl ;

    public IssueController(IssueServiceImpl issueServiceImpl){
        this.issueServiceImpl = issueServiceImpl;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get By Id Operation",response =IssueDto.class )
    public ResponseEntity<IssueDto> getById(@PathVariable(value = "id",required = true) Long id) {
        IssueDto issueDto = issueServiceImpl.getById(id);
        return ResponseEntity.ok(issueDto);
    }

    @PostMapping
    @ApiOperation(value = "Create Operation",response = IssueDto.class)
   public  ResponseEntity<IssueDto> createProject(@Valid @RequestBody IssueDto issueDto) {

        return ResponseEntity.ok( issueServiceImpl.save(issueDto));

   }

   @PutMapping("/{id}")
   @ApiOperation(value = "Update Operation",response = IssueDto.class)
   public ResponseEntity<IssueDto> updateProject (@PathVariable(value = "id",required = true) Long id,@Valid @RequestBody IssueDto issueDto ){
        return ResponseEntity.ok( issueServiceImpl.update(id,issueDto))
       ;
   }

   @DeleteMapping("/{id}")
   @ApiOperation(value = "Delete Operation",response = Boolean.class)
   public ResponseEntity<Boolean> delete(@PathVariable(value = "id",required = true) Long id){
        return ResponseEntity.ok(issueServiceImpl.delete(id));
   }

}
