package mr_hong.community.service;

import mr_hong.community.dto.PageDto;
import mr_hong.community.dto.QuestionDto;
import mr_hong.community.exception.CustomizeException;
import mr_hong.community.exception.ErrorCode;
import mr_hong.community.mapper.QuestionMapper;
import mr_hong.community.mapper.UserMapper;
import mr_hong.community.model.Question;
import mr_hong.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service                     //用于组装question和user：中间层
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    //表question中已有数据，进行分页展示
    public PageDto list(Integer page, Integer size) {

        PageDto pageDto = new PageDto();
        Integer totalCount = questionMapper.count();
        Integer totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size +1;
        }
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        pageDto.setPagination(totalPage,page);

        //分页实现,每一次都会从前端获取一个page,到HelloController更新page查询。
        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        //将question里的每个属性对应到questionDto
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);

        return pageDto;
    }

    public PageDto listByUserId(Integer useId, Integer page, Integer size) {
        PageDto pageDto = new PageDto();
        Integer totalCount = questionMapper.countByUserId(useId);

        Integer totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size +1;
        }
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        pageDto.setPagination(totalPage,page);

        //分页实现,每一次都会从前端获取一个page,到ProfileController更新page查询。
        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.listByUserId(useId,offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        //将question里的每个属性对应到questionDto
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageDto.setQuestions(questionDtoList);

        return pageDto;
    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        if(question == null){
            throw new CustomizeException(ErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findById(question.getCreator());
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.Create(question);
        }else{
            question.setGmtModified(question.getGmtCreate());
            Integer updated = questionMapper.update(question);
            if(updated != 1){
                throw new CustomizeException(ErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
}
