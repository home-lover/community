package mr_hong.community.service;

import mr_hong.community.dto.PageDto;
import mr_hong.community.dto.QuestionDto;
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
        pageDto.setPagination(totalCount,page,size);

        if(page < 1){
            page = 1;
        }
        if(page > pageDto.getTotalPage()){
            page = pageDto.getTotalPage();
        }

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
}
