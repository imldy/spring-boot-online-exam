/***********************************************************
 * @Description : 考试服务接口实现
 ***********************************************************/
package lsgwr.exam.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lsgwr.exam.entity.*;
import lsgwr.exam.enums.QuestionEnum;
import lsgwr.exam.service.ExamService;
import lsgwr.exam.repository.*;
import lsgwr.exam.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    private final ExamRecordRepository examRecordRepository;

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final QuestionLevelRepository questionLevelRepository;

    private final QuestionTypeRepository questionTypeRepository;

    private final QuestionCategoryRepository questionCategoryRepository;

    private final QuestionOptionRepository questionOptionRepository;

    public ExamServiceImpl(QuestionRepository questionRepository, UserRepository userRepository, QuestionLevelRepository questionLevelRepository, QuestionTypeRepository questionTypeRepository, QuestionCategoryRepository questionCategoryRepository, QuestionOptionRepository questionOptionRepository, ExamRepository examRepository, ExamRecordRepository examRecordRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.questionLevelRepository = questionLevelRepository;
        this.questionTypeRepository = questionTypeRepository;
        this.questionCategoryRepository = questionCategoryRepository;
        this.questionOptionRepository = questionOptionRepository;
        this.examRepository = examRepository;
        this.examRecordRepository = examRecordRepository;
    }

    @Override
    public List<QuestionVo> getQuestionAll() {
        List<Question> questionList = questionRepository.findAll();
        return getQuestionVos(questionList);
    }

    private List<QuestionVo> getQuestionVos(List<Question> questionList) {
        // 需要自定义的question列表
        List<QuestionVo> questionVoList = new ArrayList<>();
        // 循环完成每个属性的定制
        for (Question question : questionList) {
            QuestionVo questionVo = getQuestionVo(question);
            questionVoList.add(questionVo);
        }
        return questionVoList;
    }

    private QuestionVo getQuestionVo(Question question) {
        QuestionVo questionVo = new QuestionVo();
        // 先复制能复制的属性
        BeanUtils.copyProperties(question, questionVo);
        // 设置问题的创建者
        questionVo.setQuestionCreator(
                Objects.requireNonNull(
                        userRepository.findById(
                                question.getQuestionCreatorId()
                        ).orElse(null)
                ).getUserUsername());

        // 设置问题的难度
        questionVo.setQuestionLevel(
                Objects.requireNonNull(
                        questionLevelRepository.findById(
                                question.getQuestionLevelId()
                        ).orElse(null)
                ).getQuestionLevelDescription());

        // 设置题目的类别，比如单选、多选、判断等
        questionVo.setQuestionType(
                Objects.requireNonNull(
                        questionTypeRepository.findById(
                                question.getQuestionTypeId()
                        ).orElse(null)
                ).getQuestionTypeDescription());

        // 设置题目分类，比如数学、语文、英语、生活、人文等
        questionVo.setQuestionCategory(
                Objects.requireNonNull(
                        questionCategoryRepository.findById(
                                question.getQuestionCategoryId()
                        ).orElse(null)
                ).getQuestionCategoryName()
        );

        // 选项的自定义Vo列表
        List<QuestionOptionVo> optionVoList = new ArrayList<>();

        // 获得所有的选项列表
        List<QuestionOption> optionList = questionOptionRepository.findAllById(
                Arrays.asList(question.getQuestionOptionIds().split("-"))
        );

        // 获取所有的答案列表optionList中每个option的isAnswer选项
        List<QuestionOption> answerList = questionOptionRepository.findAllById(
                Arrays.asList(question.getQuestionAnswerOptionIds().split("-"))
        );

        // 根据选项和答案的id相同设置optionVo的isAnswer属性
        for (QuestionOption option : optionList) {
            QuestionOptionVo optionVo = new QuestionOptionVo();
            BeanUtils.copyProperties(option, optionVo);
            for (QuestionOption answer : answerList) {
                if (option.getQuestionOptionId().equals(answer.getQuestionOptionId())) {
                    optionVo.setAnswer(true);
                }
            }
            optionVoList.add(optionVo);
        }

        // 设置题目的所有选项
        questionVo.setQuestionOptionVoList(optionVoList);
        return questionVo;
    }

    @Override
    public QuestionVo updateQuestion(QuestionVo questionVo) {
        // 1.把需要的属性都设置好
        StringBuilder questionAnswerOptionIds = new StringBuilder();
        List<QuestionOption> questionOptionList = new ArrayList<>();
        List<QuestionOptionVo> questionOptionVoList = questionVo.getQuestionOptionVoList();
        int size = questionOptionVoList.size();
        for (int i = 0; i < questionOptionVoList.size(); i++) {
            QuestionOptionVo questionOptionVo = questionOptionVoList.get(i);
            QuestionOption questionOption = new QuestionOption();
            BeanUtils.copyProperties(questionOptionVo, questionOption);
            questionOptionList.add(questionOption);
            if (questionOptionVo.getAnswer()) {
                if (i != size - 1) {
                    // 把更新后的答案的id加上去,记得用-连到一起
                    questionAnswerOptionIds.append(questionOptionVo.getQuestionOptionId()).append("-");
                } else {
                    // 最后一个不需要用-连接
                    questionAnswerOptionIds.append(questionOptionVo.getQuestionOptionId());
                }
            }
        }

        // 1.更新问题
        Question question = questionRepository.findById(questionVo.getQuestionId()).orElse(null);
        assert question != null;
        BeanUtils.copyProperties(questionVo, question);
        question.setQuestionAnswerOptionIds(questionAnswerOptionIds.toString());
        questionRepository.save(question);

        // 2.更新所有的option
        questionOptionRepository.saveAll(questionOptionList);

        // 返回更新后的问题，方便前端局部刷新
        return getQuestionVo(question);
    }

    @Override
    public void questionCreate(QuestionCreateVo questionCreateVo) {
        // 问题创建
        Question question = new Question();
        // 把能复制的属性都复制过来
        BeanUtils.copyProperties(questionCreateVo, question);
        // 判断是否是主观题
        boolean isSubjective = questionCreateVo.getQuestionTypeId().equals(411) ||
                             questionCreateVo.getQuestionTypeId().equals(711);
        if (!isSubjective) {
            // 客观题处理逻辑
            // 设置下questionOptionIds和questionAnswerOptionIds，需要自己用Hutool生成下
            List<QuestionOption> questionOptionList = new ArrayList<>();
            List<QuestionOptionCreateVo> questionOptionCreateVoList = questionCreateVo.getQuestionOptionCreateVoList();
            for (QuestionOptionCreateVo questionOptionCreateVo : questionOptionCreateVoList) {
                QuestionOption questionOption = new QuestionOption();
                // 设置选项的的内容
                questionOption.setQuestionOptionContent(questionOptionCreateVo.getQuestionOptionContent());
                // 设置选项的id
                questionOption.setQuestionOptionId(IdUtil.simpleUUID());
                questionOptionList.add(questionOption);
            }
            // 把选项都存起来，然后才能用于下面设置Question的questionOptionIds和questionAnswerOptionIds
            questionOptionRepository.saveAll(questionOptionList);
            String questionOptionIds = "";
            String questionAnswerOptionIds = "";
            // 经过上面的saveAll方法，所有的option的主键id都已经持久化了
            for (int i = 0; i < questionOptionList.size(); i++) {
            // 获取指定选项
                QuestionOptionCreateVo questionOptionCreateVo = questionOptionCreateVoList.get(i);
                // 获取保存后的指定对象
                QuestionOption questionOption = questionOptionList.get(i);
                if (i != questionOptionList.size() - 1) {
                    questionOptionIds += questionOption.getQuestionOptionId() + "-";
                    if (questionOptionCreateVo.getAnswer()) {
                        questionAnswerOptionIds += questionOption.getQuestionOptionId() + "-";
                    }
                } else {
                    questionOptionIds += questionOption.getQuestionOptionId();
                    if (questionOptionCreateVo.getAnswer()) {
                        // 如果是答案的话
                        questionAnswerOptionIds += questionOption.getQuestionOptionId();
                    }
                }
            }
            // 设置选项id组成的字符串
            question.setQuestionOptionIds(questionOptionIds);
            // 设置答案选项id组成的字符串
            question.setQuestionAnswerOptionIds(questionAnswerOptionIds);
        } else {
            // 主观题处理逻辑
            // 设置选项id组成的字符串
            question.setQuestionOptionIds("");
            // 设置答案选项id组成的字符串
            question.setQuestionAnswerOptionIds("");
        }
        // 自己生成问题的id
        question.setQuestionId(IdUtil.simpleUUID());
        // 先把创建时间和更新时间每次都取当前时间吧
        question.setCreateTime(new Date());
        question.setUpdateTime(new Date());
        // 保存问题到数据库
        questionRepository.save(question);
    }

    @Override
    public QuestionSelectionVo getSelections() {
        QuestionSelectionVo questionSelectionVo = new QuestionSelectionVo();
        questionSelectionVo.setQuestionCategoryList(questionCategoryRepository.findAll());
        questionSelectionVo.setQuestionLevelList(questionLevelRepository.findAll());
        questionSelectionVo.setQuestionTypeList(questionTypeRepository.findAll());

        return questionSelectionVo;
    }

    /**
     * 去除字符串最后的，防止split的时候出错
     *
     * @param str 原始字符串
     * @return
     */
    public static String trimMiddleLine(String str) {
        if (str.charAt(str.length() - 1) == '-') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    @Override
    public QuestionDetailVo getQuestionDetail(String id) {
        Question question = questionRepository.findById(id).orElse(null);
        QuestionDetailVo questionDetailVo = new QuestionDetailVo();
        questionDetailVo.setId(id);
        questionDetailVo.setName(question.getQuestionName());
        questionDetailVo.setDescription(question.getQuestionDescription());
        // 问题类型，单选题/多选题/判断题
        questionDetailVo.setType(
                Objects.requireNonNull(
                        questionTypeRepository.findById(
                                question.getQuestionTypeId()
                        ).orElse(null)
                ).getQuestionTypeDescription()
        );
        // 获取当前问题的选项
        String optionIdsStr = trimMiddleLine(question.getQuestionOptionIds());
        String[] optionIds = optionIdsStr.split("-");
        // 获取选项列表
        List<QuestionOption> optionList = questionOptionRepository.findAllById(Arrays.asList(optionIds));
        questionDetailVo.setOptions(optionList);
        return questionDetailVo;
    }

    @Override
    public List<ExamVo> getExamAll() {
        List<Exam> examList = examRepository.findAll();
        return getExamVos(examList);
    }

    private List<ExamVo> getExamVos(List<Exam> examList) {
        // 需要自定义的exam列表
        List<ExamVo> examVoList = new ArrayList<>();
        // 循环完成每个属性的定制
        for (Exam exam : examList) {
            ExamVo examVo = new ExamVo();
            // 先尽量复制能复制的所有属性
            BeanUtils.copyProperties(exam, examVo);
            // 设置问题的创建者
            examVo.setExamCreator(
                    Objects.requireNonNull(
                            userRepository.findById(
                                    exam.getExamCreatorId()
                            ).orElse(null)
                    ).getUserUsername()
            );

            // 获取所有单选题列表，并赋值到ExamVo的属性ExamQuestionSelectVoRadioList上
            List<ExamQuestionSelectVo> radioQuestionVoList = new ArrayList<>();
            List<Question> radioQuestionList = questionRepository.findAllById(
                    Arrays.asList(exam.getExamQuestionIdsRadio().split("-"))
            );
            for (Question question : radioQuestionList) {
                ExamQuestionSelectVo radioQuestionVo = new ExamQuestionSelectVo();
                BeanUtils.copyProperties(question, radioQuestionVo);
                radioQuestionVo.setChecked(true); // 考试中的问题肯定被选中的
                radioQuestionVoList.add(radioQuestionVo);
            }
            examVo.setExamQuestionSelectVoRadioList(radioQuestionVoList);

            // 获取所有多选题列表，并赋值到ExamVo的属性ExamQuestionSelectVoCheckList上
            List<ExamQuestionSelectVo> checkQuestionVoList = new ArrayList<>();
            List<Question> checkQuestionList = questionRepository.findAllById(
                    Arrays.asList(exam.getExamQuestionIdsCheck().split("-"))
            );
            for (Question question : checkQuestionList) {
                ExamQuestionSelectVo checkQuestionVo = new ExamQuestionSelectVo();
                BeanUtils.copyProperties(question, checkQuestionVo);
                checkQuestionVo.setChecked(true); // 考试中的问题肯定被选中的
                checkQuestionVoList.add(checkQuestionVo);
            }
            examVo.setExamQuestionSelectVoCheckList(checkQuestionVoList);

            // 获取所有多选题列表，并赋值到ExamVo的属性ExamQuestionSelectVoJudgeList上
            List<ExamQuestionSelectVo> judgeQuestionVoList = new ArrayList<>();
            List<Question> judgeQuestionList = questionRepository.findAllById(
                    Arrays.asList(exam.getExamQuestionIdsJudge().split("-"))
            );
            for (Question question : judgeQuestionList) {
                ExamQuestionSelectVo judgeQuestionVo = new ExamQuestionSelectVo();
                BeanUtils.copyProperties(question, judgeQuestionVo);
                judgeQuestionVo.setChecked(true); // 考试中的问题肯定被选中的
                judgeQuestionVoList.add(judgeQuestionVo);
            }
            examVo.setExamQuestionSelectVoJudgeList(judgeQuestionVoList);

            // 把examVo加到examVoList中
            examVoList.add(examVo);
        }
        return examVoList;
    }



    @Override
    public ExamQuestionTypeVo getExamQuestionType() {
        ExamQuestionTypeVo examQuestionTypeVo = new ExamQuestionTypeVo();

        // 获取写作题列表
        List<ExamQuestionSelectVo> partIQuestionVoList = new ArrayList<>();
        List<Question> partIQuestionList = questionRepository.findByQuestionTypeId(QuestionEnum.PART_I.getId());
        for (Question question : partIQuestionList) {
            ExamQuestionSelectVo partIQuestionVo = new ExamQuestionSelectVo();
            BeanUtils.copyProperties(question, partIQuestionVo);
            partIQuestionVoList.add(partIQuestionVo);
        }
        examQuestionTypeVo.setExamQuestionSelectVoPartIList(partIQuestionVoList);

        // 获取听力A部分题目列表
        List<ExamQuestionSelectVo> partIIAQuestionVoList = new ArrayList<>();
        List<Question> partIIAQuestionList = questionRepository.findByQuestionTypeId(QuestionEnum.PART_II_A.getId());
        for (Question question : partIIAQuestionList) {
            ExamQuestionSelectVo partIIAQuestionVo = new ExamQuestionSelectVo();
            BeanUtils.copyProperties(question, partIIAQuestionVo);
            partIIAQuestionVoList.add(partIIAQuestionVo);
        }
        examQuestionTypeVo.setExamQuestionSelectVoPartIIAList(partIIAQuestionVoList);

        // 获取听力B部分题目列表
        List<ExamQuestionSelectVo> partIIBQuestionVoList = new ArrayList<>();
        List<Question> partIIBQuestionList = questionRepository.findByQuestionTypeId(QuestionEnum.PART_II_B.getId());
        for (Question question : partIIBQuestionList) {
            ExamQuestionSelectVo partIIBQuestionVo = new ExamQuestionSelectVo();
            BeanUtils.copyProperties(question, partIIBQuestionVo);
            partIIBQuestionVoList.add(partIIBQuestionVo);
        }
        examQuestionTypeVo.setExamQuestionSelectVoPartIIBList(partIIBQuestionVoList);

        // 获取听力C部分题目列表
        List<ExamQuestionSelectVo> partIICQuestionVoList = new ArrayList<>();
        List<Question> partIICQuestionList = questionRepository.findByQuestionTypeId(QuestionEnum.PART_II_C.getId());
        for (Question question : partIICQuestionList) {
            ExamQuestionSelectVo partIICQuestionVo = new ExamQuestionSelectVo();
            BeanUtils.copyProperties(question, partIICQuestionVo);
            partIICQuestionVoList.add(partIICQuestionVo);
        }
        examQuestionTypeVo.setExamQuestionSelectVoPartIICList(partIICQuestionVoList);

        // 获取阅读A部分题目列表
        List<ExamQuestionSelectVo> partIIIAQuestionVoList = new ArrayList<>();
        List<Question> partIIIAQuestionList = questionRepository.findByQuestionTypeId(QuestionEnum.PART_III_A.getId());
        for (Question question : partIIIAQuestionList) {
            ExamQuestionSelectVo partIIIAQuestionVo = new ExamQuestionSelectVo();
            BeanUtils.copyProperties(question, partIIIAQuestionVo);
            partIIIAQuestionVoList.add(partIIIAQuestionVo);
        }
        examQuestionTypeVo.setExamQuestionSelectVoPartIIIAList(partIIIAQuestionVoList);

        // 获取阅读B部分题目列表
        List<ExamQuestionSelectVo> partIIIBQuestionVoList = new ArrayList<>();
        List<Question> partIIIBQuestionList = questionRepository.findByQuestionTypeId(QuestionEnum.PART_III_B.getId());
        for (Question question : partIIIBQuestionList) {
            ExamQuestionSelectVo partIIIBQuestionVo = new ExamQuestionSelectVo();
            BeanUtils.copyProperties(question, partIIIBQuestionVo);
            partIIIBQuestionVoList.add(partIIIBQuestionVo);
        }
        examQuestionTypeVo.setExamQuestionSelectVoPartIIIBList(partIIIBQuestionVoList);

        // 获取阅读C部分题目列表
        List<ExamQuestionSelectVo> partIIICQuestionVoList = new ArrayList<>();
        List<Question> partIIICQuestionList = questionRepository.findByQuestionTypeId(QuestionEnum.PART_III_C.getId());
        for (Question question : partIIICQuestionList) {
            ExamQuestionSelectVo partIIICQuestionVo = new ExamQuestionSelectVo();
            BeanUtils.copyProperties(question, partIIICQuestionVo);
            partIIICQuestionVoList.add(partIIICQuestionVo);
        }
        examQuestionTypeVo.setExamQuestionSelectVoPartIIICList(partIIICQuestionVoList);

        // 获取翻译题列表
        List<ExamQuestionSelectVo> partIVQuestionVoList = new ArrayList<>();
        List<Question> partIVQuestionList = questionRepository.findByQuestionTypeId(QuestionEnum.PART_IV.getId());
        for (Question question : partIVQuestionList) {
            ExamQuestionSelectVo partIVQuestionVo = new ExamQuestionSelectVo();
            BeanUtils.copyProperties(question, partIVQuestionVo);
            partIVQuestionVoList.add(partIVQuestionVo);
        }
        examQuestionTypeVo.setExamQuestionSelectVoPartIVList(partIVQuestionVoList);

        return examQuestionTypeVo;
    }

    @Override
    public Exam create(ExamCreateVo examCreateVo, String userId) {
        // 在线考试系统创建
        Exam exam = new Exam();
        BeanUtils.copyProperties(examCreateVo, exam);
        exam.setExamId(IdUtil.simpleUUID());
        exam.setExamCreatorId(userId);
        exam.setCreateTime(new Date());
        exam.setUpdateTime(new Date());
        // Todo:这两个日志后面是要在前端传入的，这里暂时定为当前日期
        exam.setExamStartDate(new Date());
        exam.setExamEndDate(new Date());
        String radioIdsStr = "";
        String checkIdsStr = "";
        String judgeIdsStr = "";
        String partIIdsStr = "";
        String partIIAIdsStr = "";
        String partIIBIdsStr = "";
        String partIICIdsStr = "";
        String partIIIAIdsStr = "";
        String partIIIBIdsStr = "";
        String partIIICIdsStr = "";
        String partIVIdsStr = "";

        List<ExamQuestionSelectVo> partIs = examCreateVo.getPartIs();
        List<ExamQuestionSelectVo> partIIAs = examCreateVo.getPartIIAs();
        List<ExamQuestionSelectVo> partIIBs = examCreateVo.getPartIIBs();
        List<ExamQuestionSelectVo> partIICs = examCreateVo.getPartIICs();
        List<ExamQuestionSelectVo> partIIIAs = examCreateVo.getPartIIIAs();
        List<ExamQuestionSelectVo> partIIIBs = examCreateVo.getPartIIIBs();
        List<ExamQuestionSelectVo> partIIICs = examCreateVo.getPartIIICs();
        List<ExamQuestionSelectVo> partIVs = examCreateVo.getPartIVs();

        int partICnt = 0, partIIACnt = 0, partIIBCnt = 0, partIICCnt = 0;
        int partIIIACnt = 0, partIIIBCnt = 0, partIIICCnt = 0, partIVCnt = 0;
        
        // 处理Part I写作题
        for (ExamQuestionSelectVo partI : partIs) {
            if (partI.getChecked()) {
                partIIdsStr += partI.getQuestionId() + "-";
                partICnt++;
            }
        }
        partIIdsStr = replaceLastSeparator(partIIdsStr);
        
        // 处理Part II听力A部分
        for (ExamQuestionSelectVo partIIA : partIIAs) {
            if (partIIA.getChecked()) {
                partIIAIdsStr += partIIA.getQuestionId() + "-";
                partIIACnt++;
            }
        }
        partIIAIdsStr = replaceLastSeparator(partIIAIdsStr);
        
        // 处理Part II听力B部分
        for (ExamQuestionSelectVo partIIB : partIIBs) {
            if (partIIB.getChecked()) {
                partIIBIdsStr += partIIB.getQuestionId() + "-";
                partIIBCnt++;
            }
        }
        partIIBIdsStr = replaceLastSeparator(partIIBIdsStr);
        
        // 处理Part II听力C部分
        for (ExamQuestionSelectVo partIIC : partIICs) {
            if (partIIC.getChecked()) {
                partIICIdsStr += partIIC.getQuestionId() + "-";
                partIICCnt++;
            }
        }
        partIICIdsStr = replaceLastSeparator(partIICIdsStr);
        
        // 处理Part III阅读A部分
        for (ExamQuestionSelectVo partIIIA : partIIIAs) {
            if (partIIIA.getChecked()) {
                partIIIAIdsStr += partIIIA.getQuestionId() + "-";
                partIIIACnt++;
            }
        }
        partIIIAIdsStr = replaceLastSeparator(partIIIAIdsStr);
        
        // 处理Part III阅读B部分
        for (ExamQuestionSelectVo partIIIB : partIIIBs) {
            if (partIIIB.getChecked()) {
                partIIIBIdsStr += partIIIB.getQuestionId() + "-";
                partIIIBCnt++;
            }
        }
        partIIIBIdsStr = replaceLastSeparator(partIIIBIdsStr);
        
        // 处理Part III阅读C部分
        for (ExamQuestionSelectVo partIIIC : partIIICs) {
            if (partIIIC.getChecked()) {
                partIIICIdsStr += partIIIC.getQuestionId() + "-";
                partIIICCnt++;
            }
        }
        partIIICIdsStr = replaceLastSeparator(partIIICIdsStr);
        
        // 处理Part IV翻译题
        for (ExamQuestionSelectVo partIV : partIVs) {
            if (partIV.getChecked()) {
                partIVIdsStr += partIV.getQuestionId() + "-";
                partIVCnt++;
            }
        }
        partIVIdsStr = replaceLastSeparator(partIVIdsStr);
        
        // 设置所有题目的id
        exam.setExamQuestionIds(radioIdsStr + "-" + checkIdsStr + "-" + judgeIdsStr + "-" + 
                              partIIdsStr + "-" + partIIAIdsStr + "-" + partIIBIdsStr + "-" + partIICIdsStr + "-" + 
                              partIIIAIdsStr + "-" + partIIIBIdsStr + "-" + partIIICIdsStr + "-" + partIVIdsStr);
        
        // 设置各个题型的id
        exam.setExamQuestionIdsRadio(radioIdsStr);
        exam.setExamQuestionIdsCheck(checkIdsStr);
        exam.setExamQuestionIdsJudge(judgeIdsStr);
        exam.setExamQuestionIdsPartI(partIIdsStr);
        exam.setExamQuestionIdsPartIIA(partIIAIdsStr);
        exam.setExamQuestionIdsPartIIB(partIIBIdsStr);
        exam.setExamQuestionIdsPartIIC(partIICIdsStr);
        exam.setExamQuestionIdsPartIIIA(partIIIAIdsStr);
        exam.setExamQuestionIdsPartIIIB(partIIIBIdsStr);
        exam.setExamQuestionIdsPartIIIC(partIIICIdsStr);
        exam.setExamQuestionIdsPartIV(partIVIdsStr);

        // 计算总分数
        int examScore =
                       partICnt * exam.getExamScorePartI() +
                       partIIACnt * exam.getExamScorePartIIA() +
                       partIIBCnt * exam.getExamScorePartIIB() +
                       partIICCnt * exam.getExamScorePartIIC() +
                       partIIIACnt * exam.getExamScorePartIIIA() +
                       partIIIBCnt * exam.getExamScorePartIIIB() +
                       partIIICCnt * exam.getExamScorePartIIIC() +
                       partIVCnt * exam.getExamScorePartIV();
        exam.setExamScore(examScore);
        examRepository.save(exam);
        return exam;
    }

    @Override
    public Exam update(ExamVo examVo, String userId) {
        Exam exam = new Exam();
        BeanUtils.copyProperties(examVo, exam);
        exam.setExamCreatorId(userId); // 考试的更新人为最新的创建人
        exam.setUpdateTime(new Date()); // 考试的更新日期要记录下

        String radioIdsStr = "";
        String checkIdsStr = "";
        String judgeIdsStr = "";
        String partIIdsStr = "";
        String partIIAIdsStr = "";
        String partIIBIdsStr = "";
        String partIICIdsStr = "";
        String partIIIAIdsStr = "";
        String partIIIBIdsStr = "";
        String partIIICIdsStr = "";
        String partIVIdsStr = "";
        
        List<ExamQuestionSelectVo> radios = examVo.getExamQuestionSelectVoRadioList();
        List<ExamQuestionSelectVo> checks = examVo.getExamQuestionSelectVoCheckList();
        List<ExamQuestionSelectVo> judges = examVo.getExamQuestionSelectVoJudgeList();
        List<ExamQuestionSelectVo> partIs = examVo.getExamQuestionSelectVoPartIList();
        List<ExamQuestionSelectVo> partIIAs = examVo.getExamQuestionSelectVoPartIIAList();
        List<ExamQuestionSelectVo> partIIBs = examVo.getExamQuestionSelectVoPartIIBList();
        List<ExamQuestionSelectVo> partIICs = examVo.getExamQuestionSelectVoPartIICList();
        List<ExamQuestionSelectVo> partIIIAs = examVo.getExamQuestionSelectVoPartIIIAList();
        List<ExamQuestionSelectVo> partIIIBs = examVo.getExamQuestionSelectVoPartIIIBList();
        List<ExamQuestionSelectVo> partIIICs = examVo.getExamQuestionSelectVoPartIIICList();
        List<ExamQuestionSelectVo> partIVs = examVo.getExamQuestionSelectVoPartIVList();
        
        int radioCnt = 0, checkCnt = 0, judgeCnt = 0;
        int partICnt = 0, partIIACnt = 0, partIIBCnt = 0, partIICCnt = 0;
        int partIIIACnt = 0, partIIIBCnt = 0, partIIICCnt = 0, partIVCnt = 0;
        
        // 处理单选题
        for (ExamQuestionSelectVo radio : radios) {
            if (radio.getChecked()) {
                radioIdsStr += radio.getQuestionId() + "-";
                radioCnt++;
            }
        }
        radioIdsStr = replaceLastSeparator(radioIdsStr);
        
        // 处理多选题
        for (ExamQuestionSelectVo check : checks) {
            if (check.getChecked()) {
                checkIdsStr += check.getQuestionId() + "-";
                checkCnt++;
            }
        }
        checkIdsStr = replaceLastSeparator(checkIdsStr);
        
        // 处理判断题
        for (ExamQuestionSelectVo judge : judges) {
            if (judge.getChecked()) {
                judgeIdsStr += judge.getQuestionId() + "-";
                judgeCnt++;
            }
        }
        judgeIdsStr = replaceLastSeparator(judgeIdsStr);
        
        // 处理Part I写作题
        for (ExamQuestionSelectVo partI : partIs) {
            if (partI.getChecked()) {
                partIIdsStr += partI.getQuestionId() + "-";
                partICnt++;
            }
        }
        partIIdsStr = replaceLastSeparator(partIIdsStr);
        
        // 处理Part II听力A部分
        for (ExamQuestionSelectVo partIIA : partIIAs) {
            if (partIIA.getChecked()) {
                partIIAIdsStr += partIIA.getQuestionId() + "-";
                partIIACnt++;
            }
        }
        partIIAIdsStr = replaceLastSeparator(partIIAIdsStr);
        
        // 处理Part II听力B部分
        for (ExamQuestionSelectVo partIIB : partIIBs) {
            if (partIIB.getChecked()) {
                partIIBIdsStr += partIIB.getQuestionId() + "-";
                partIIBCnt++;
            }
        }
        partIIBIdsStr = replaceLastSeparator(partIIBIdsStr);
        
        // 处理Part II听力C部分
        for (ExamQuestionSelectVo partIIC : partIICs) {
            if (partIIC.getChecked()) {
                partIICIdsStr += partIIC.getQuestionId() + "-";
                partIICCnt++;
            }
        }
        partIICIdsStr = replaceLastSeparator(partIICIdsStr);
        
        // 处理Part III阅读A部分
        for (ExamQuestionSelectVo partIIIA : partIIIAs) {
            if (partIIIA.getChecked()) {
                partIIIAIdsStr += partIIIA.getQuestionId() + "-";
                partIIIACnt++;
            }
        }
        partIIIAIdsStr = replaceLastSeparator(partIIIAIdsStr);
        
        // 处理Part III阅读B部分
        for (ExamQuestionSelectVo partIIIB : partIIIBs) {
            if (partIIIB.getChecked()) {
                partIIIBIdsStr += partIIIB.getQuestionId() + "-";
                partIIIBCnt++;
            }
        }
        partIIIBIdsStr = replaceLastSeparator(partIIIBIdsStr);
        
        // 处理Part III阅读C部分
        for (ExamQuestionSelectVo partIIIC : partIIICs) {
            if (partIIIC.getChecked()) {
                partIIICIdsStr += partIIIC.getQuestionId() + "-";
                partIIICCnt++;
            }
        }
        partIIICIdsStr = replaceLastSeparator(partIIICIdsStr);
        
        // 处理Part IV翻译题
        for (ExamQuestionSelectVo partIV : partIVs) {
            if (partIV.getChecked()) {
                partIVIdsStr += partIV.getQuestionId() + "-";
                partIVCnt++;
            }
        }
        partIVIdsStr = replaceLastSeparator(partIVIdsStr);
        
        // 设置所有题目的id
        exam.setExamQuestionIds(radioIdsStr + "-" + checkIdsStr + "-" + judgeIdsStr + "-" + 
                              partIIdsStr + "-" + partIIAIdsStr + "-" + partIIBIdsStr + "-" + partIICIdsStr + "-" + 
                              partIIIAIdsStr + "-" + partIIIBIdsStr + "-" + partIIICIdsStr + "-" + partIVIdsStr);
        
        // 设置各个题型的id
        exam.setExamQuestionIdsRadio(radioIdsStr);
        exam.setExamQuestionIdsCheck(checkIdsStr);
        exam.setExamQuestionIdsJudge(judgeIdsStr);
        exam.setExamQuestionIdsPartI(partIIdsStr);
        exam.setExamQuestionIdsPartIIA(partIIAIdsStr);
        exam.setExamQuestionIdsPartIIB(partIIBIdsStr);
        exam.setExamQuestionIdsPartIIC(partIICIdsStr);
        exam.setExamQuestionIdsPartIIIA(partIIIAIdsStr);
        exam.setExamQuestionIdsPartIIIB(partIIIBIdsStr);
        exam.setExamQuestionIdsPartIIIC(partIIICIdsStr);
        exam.setExamQuestionIdsPartIV(partIVIdsStr);

        // 计算总分数
        int examScore = radioCnt * exam.getExamScoreRadio() + 
                       checkCnt * exam.getExamScoreCheck() + 
                       judgeCnt * exam.getExamScoreJudge() +
                       partICnt * exam.getExamScorePartI() +
                       partIIACnt * exam.getExamScorePartIIA() +
                       partIIBCnt * exam.getExamScorePartIIB() +
                       partIICCnt * exam.getExamScorePartIIC() +
                       partIIIACnt * exam.getExamScorePartIIIA() +
                       partIIIBCnt * exam.getExamScorePartIIIB() +
                       partIIICCnt * exam.getExamScorePartIIIC() +
                       partIVCnt * exam.getExamScorePartIV();
        exam.setExamScore(examScore);
        examRepository.save(exam);
        return exam;
    }

    @Override
    public List<ExamCardVo> getExamCardList() {
        List<Exam> examList = examRepository.findAll();
        List<ExamCardVo> examCardVoList = new ArrayList<>();
        for (Exam exam : examList) {
            ExamCardVo examCardVo = new ExamCardVo();
            BeanUtils.copyProperties(exam, examCardVo);
            examCardVoList.add(examCardVo);
        }
        return examCardVoList;
    }

    @Override
    public ExamDetailVo getExamDetail(String id) {
        Exam exam = examRepository.findById(id).orElse(null);
        ExamDetailVo examDetailVo = new ExamDetailVo();
        examDetailVo.setExam(exam);
        assert exam != null;
        examDetailVo.setRadioIds(exam.getExamQuestionIdsRadio().split("-"));
        examDetailVo.setCheckIds(exam.getExamQuestionIdsCheck().split("-"));
        examDetailVo.setJudgeIds(exam.getExamQuestionIdsJudge().split("-"));
        examDetailVo.setPartIIds(exam.getExamQuestionIdsPartI().split("-"));
        examDetailVo.setPartIIAIds(exam.getExamQuestionIdsPartIIA().split("-"));
        examDetailVo.setPartIIBIds(exam.getExamQuestionIdsPartIIB().split("-"));
        examDetailVo.setPartIICIds(exam.getExamQuestionIdsPartIIC().split("-"));
        examDetailVo.setPartIIIAIds(exam.getExamQuestionIdsPartIIIA().split("-"));
        examDetailVo.setPartIIIBIds(exam.getExamQuestionIdsPartIIIB().split("-"));
        examDetailVo.setPartIIICIds(exam.getExamQuestionIdsPartIIIC().split("-"));
        examDetailVo.setPartIVIds(exam.getExamQuestionIdsPartIV().split("-"));
        return examDetailVo;
    }

    @Override
    public ExamRecord judge(String userId, String examId, HashMap<String, List<String>> answersMap) {
        // 开始考试判分啦~~~
        // 1.首先获取考试对象和选项数组
        ExamDetailVo examDetailVo = getExamDetail(examId);
        Exam exam = examDetailVo.getExam();
        // 2.然后获取该考试下所有的题目信息
        List<String> questionIds = new ArrayList<>();
        // 2.1 题目id的数组
        List<String> radioIdList = Arrays.asList(examDetailVo.getRadioIds());
        List<String> checkIdList = Arrays.asList(examDetailVo.getCheckIds());
        List<String> judgeIdList = Arrays.asList(examDetailVo.getJudgeIds());
        List<String> partIIdList = Arrays.asList(examDetailVo.getPartIIds());
        List<String> partIIAIdList = Arrays.asList(examDetailVo.getPartIIAIds());
        List<String> partIIBIdList = Arrays.asList(examDetailVo.getPartIIBIds());
        List<String> partIICIdList = Arrays.asList(examDetailVo.getPartIICIds());
        List<String> partIIIAIdList = Arrays.asList(examDetailVo.getPartIIIAIds());
        List<String> partIIIBIdList = Arrays.asList(examDetailVo.getPartIIIBIds());
        List<String> partIIICIdList = Arrays.asList(examDetailVo.getPartIIICIds());
        List<String> partIVIdList = Arrays.asList(examDetailVo.getPartIVIds());
        
        questionIds.addAll(radioIdList);
        questionIds.addAll(checkIdList);
        questionIds.addAll(judgeIdList);
        questionIds.addAll(partIIdList);
        questionIds.addAll(partIIAIdList);
        questionIds.addAll(partIIBIdList);
        questionIds.addAll(partIICIdList);
        questionIds.addAll(partIIIAIdList);
        questionIds.addAll(partIIIBIdList);
        questionIds.addAll(partIIICIdList);
        questionIds.addAll(partIVIdList);
        
        // 2.2 每种题目的分数
        int radioScore = exam.getExamScoreRadio();
        int checkScore = exam.getExamScoreCheck();
        int judgeScore = exam.getExamScoreJudge();
        int partIScore = exam.getExamScorePartI();
        int partIIAScore = exam.getExamScorePartIIA();
        int partIIBScore = exam.getExamScorePartIIB();
        int partIICScore = exam.getExamScorePartIIC();
        int partIIIAScore = exam.getExamScorePartIIIA();
        int partIIIBScore = exam.getExamScorePartIIIB();
        int partIIICScore = exam.getExamScorePartIIIC();
        int partIVScore = exam.getExamScorePartIV();
        
        // 2.3 根据问题id的数组拿到所有的问题对象，供下面步骤用
        List<Question> questionList = questionRepository.findAllById(questionIds);
        Map<String, Question> questionMap = new HashMap<>();
        for (Question question : questionList) {
            questionMap.put(question.getQuestionId(), question);
        }
        // 3.根据正确答案和用户作答信息进行判分
        Set<String> questionIdsAnswer = answersMap.keySet();
        // 存储当前考试每个题目的得分情况
        Map<String, Integer> judgeMap = new HashMap<>();
        // 考生作答地每个题目的选项(题目和题目之间用$分隔，题目有多个选项地话用-分隔,题目和选项之间用_分隔),用于查看考试详情
        // 例子：题目1的id_作答选项1-作答选项2&题目2的id_作答选项1&题目3_作答选项1-作答选项2-作答选项3
        StringBuilder answerOptionIdsSb = new StringBuilder();
        // 用户此次考试的总分
        int totalScore = 0;
        for (String questionId : questionIdsAnswer) {
            // 获取用户作答地这个题的答案信息
            Question question = questionMap.get(questionId);
            // 获取答案选项
            String questionAnswerOptionIds = replaceLastSeparator(question.getQuestionAnswerOptionIds());
            List<String> questionAnswerOptionIdList = Arrays.asList(questionAnswerOptionIds.split("-"));
            Collections.sort(questionAnswerOptionIdList);
            String answerStr = listConcat(questionAnswerOptionIdList);
            // 获取用户作答
            List<String> questionUserOptionIdList = answersMap.get(questionId);
            Collections.sort(questionUserOptionIdList);
            String userStr = listConcat(questionUserOptionIdList);
            // 判断questionAnswerOptionIds和answersMap里面的答案是否相等
            if (answerStr.equals(userStr)) {
                // 说明题目作答正确,下面根据题型给分
                int score = 0;
                if (radioIdList.contains(questionId)) {
                    score = radioScore;
                }
                if (checkIdList.contains(questionId)) {
                    score = checkScore;
                }
                if (judgeIdList.contains(questionId)) {
                    score = judgeScore;
                }
                if (partIIdList.contains(questionId)) {
                    score = partIScore;
                }
                if (partIIAIdList.contains(questionId)) {
                    score = partIIAScore;
                }
                if (partIIBIdList.contains(questionId)) {
                    score = partIIBScore;
                }
                if (partIICIdList.contains(questionId)) {
                    score = partIICScore;
                }
                if (partIIIAIdList.contains(questionId)) {
                    score = partIIIAScore;
                }
                if (partIIIBIdList.contains(questionId)) {
                    score = partIIIBScore;
                }
                if (partIIICIdList.contains(questionId)) {
                    score = partIIICScore;
                }
                if (partIVIdList.contains(questionId)) {
                    score = partIVScore;
                }
                // 累计本次考试得分
                totalScore += score;
                // True代表题目答对
                answerOptionIdsSb.append(questionId + "@True_" + userStr + "$");
                judgeMap.put(questionId, score);
            } else {
                // 说明题目作答错误,直接判零分,False代表题目答错
                answerOptionIdsSb.append(questionId + "@False_" + userStr + "$");
                judgeMap.put(questionId, 0);
            }
        }
        // 4.计算得分，记录本次考试结果，存到ExamRecord中
        ExamRecord examRecord = new ExamRecord();
        examRecord.setExamRecordId(IdUtil.simpleUUID());
        examRecord.setExamId(examId);
        // 注意去掉最后可能有的&_-
        examRecord.setAnswerOptionIds(replaceLastSeparator(answerOptionIdsSb.toString()));
        examRecord.setExamJoinerId(userId);
        examRecord.setExamJoinDate(new Date());
        examRecord.setExamJoinScore(totalScore);
        examRecordRepository.save(examRecord);
        return examRecord;
    }

    @Override
    public List<ExamRecordVo> getExamRecordList(String userId) {
        // 获取指定用户下的考试记录列表
        List<ExamRecord> examRecordList = examRecordRepository.findByExamJoinerIdOrderByExamJoinDateDesc(userId);
        List<ExamRecordVo> examRecordVoList = new ArrayList<>();
        for (ExamRecord examRecord : examRecordList) {
            ExamRecordVo examRecordVo = new ExamRecordVo();
            Exam exam = examRepository.findById(examRecord.getExamId()).orElse(null);
            examRecordVo.setExam(exam);
            User user = userRepository.findById(userId).orElse(null);
            examRecordVo.setUser(user);
            examRecordVo.setExamRecord(examRecord);
            examRecordVoList.add(examRecordVo);
        }
        return examRecordVoList;
    }

    @Override
    public RecordDetailVo getRecordDetail(String recordId) {
        // 获取考试详情的封装对象
        ExamRecord record = examRecordRepository.findById(recordId).orElse(null);
        RecordDetailVo recordDetailVo = new RecordDetailVo();
        recordDetailVo.setExamRecord(record);
        // 用户的答案，需要解析
        HashMap<String, List<String>> answersMap = new HashMap<>();
        HashMap<String, String> resultsMap = new HashMap<>();
        assert record != null;
        String answersStr = record.getAnswerOptionIds();
        // $分隔题目,因为$在正则中有特殊用途(行尾)，所以需要括起来
        String[] questionArr = answersStr.split("[$]");
        for (String questionStr : questionArr) {
            System.out.println(questionStr);
            // 区分开题目标题和选项
            String[] questionTitleResultAndOption = questionStr.split("_");
            String[] questionTitleAndResult = questionTitleResultAndOption[0].split("@");
            String[] questionOptions = questionTitleResultAndOption[1].split("-");
            // 题目：答案选项
            answersMap.put(questionTitleAndResult[0], Arrays.asList(questionOptions));
            // 题目：True / False
            resultsMap.put(questionTitleAndResult[0], questionTitleAndResult[1]);
        }
        recordDetailVo.setAnswersMap(answersMap);
        recordDetailVo.setResultsMap(resultsMap);
        // 下面再计算正确答案的map
        ExamDetailVo examDetailVo = getExamDetail(record.getExamId());
        List<String> questionIdList = new ArrayList<>();
        questionIdList.addAll(Arrays.asList(examDetailVo.getRadioIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getCheckIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getJudgeIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getPartIIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getPartIIAIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getPartIIBIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getPartIICIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getPartIIIAIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getPartIIIBIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getPartIIICIds()));
        questionIdList.addAll(Arrays.asList(examDetailVo.getPartIVIds()));
        // 获取所有的问题对象
        List<Question> questionList = questionRepository.findAllById(questionIdList);
        HashMap<String, List<String>> answersRightMap = new HashMap<>();
        for (Question question : questionList) {
            // 记得去掉最后可能出现的特殊字符
            String questionAnswerOptionIdsStr = replaceLastSeparator(question.getQuestionAnswerOptionIds());
            String[] questionAnswerOptionIds = questionAnswerOptionIdsStr.split("-");
            answersRightMap.put(question.getQuestionId(), Arrays.asList(questionAnswerOptionIds));
        }
        recordDetailVo.setAnswersRightMap(answersRightMap);
        return recordDetailVo;
    }

    /**
     * 把字符串最后一个字符-替换掉
     *
     * @param str 原始字符串
     * @return 替换掉最后一个-的字符串
     */
    private String replaceLastSeparator(String str) {
        // 判断字符串是否为空或长度为0
        if (str == null || str.length() == 0) {
            return str;
        }
        String lastChar = str.substring(str.length() - 1);
        // 题目和题目之间用$分隔，题目有多个选项地话用-分隔,题目和选项之间用_分隔
        if ("-".equals(lastChar) || "_".equals(lastChar) || "$".equals(lastChar)) {
            str = StrUtil.sub(str, 0, str.length() - 1);
        }
        return str;
    }

    /**
     * 把字符串用-连接起来
     *
     * @param strList 字符串列表
     * @return 拼接好的字符串，记住要去掉最后面的-
     */
    private String listConcat(List<String> strList) {
        StringBuilder sb = new StringBuilder();
        for (String str : strList) {
            sb.append(str);
            sb.append("-");
        }
        return replaceLastSeparator(sb.toString());
    }
}
