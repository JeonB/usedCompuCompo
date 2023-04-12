package thwjd.usedbook.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import thwjd.usedbook.entity.Member;
import thwjd.usedbook.entity.ValidCheckResponse;
import thwjd.usedbook.repository.MemberRepositoryMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {

    @Autowired
    private MemberRepositoryMapper memberRepository;

    public BindingResult loginValidCheck(Member member, BindingResult bindingResult){

        Member emailCheck = memberRepository.findByEmail(member.getEmail()).orElse(null);
        Member emailAndpasswordCheck = memberRepository.findByEmail(member.getEmail())
                .filter(m -> m.getPassword().equals(member.getPassword()))
                .orElse(null);

        if(bindingResult.hasFieldErrors("email")){
            return bindingResult;
        }
        if(emailCheck == null){
            bindingResult.rejectValue("email", "EmailFoundFail", "일치하는 이메일이 없습니다");
            return bindingResult;
        }
        if(emailAndpasswordCheck == null){
            bindingResult.reject("PasswordFail", "비밀번호를 확인해주세요");
            return bindingResult;
        }
        return null;
    }


    public BindingResult findPasswordValidCheck(Member member, BindingResult bindingResult){
        Member find = memberRepository.findByEmailAndName(member.getEmail(), member.getName()).orElse(null);
        if(find == null) {
            bindingResult.reject("findPasswordFail", "일치하는 회원이 없습니다.");
            return bindingResult;
        }
        return null;
    }




    public List registerValidCheck(Member member, BindingResult bindingResult){

        List<ValidCheckResponse> response = new ArrayList<>();
        Member emailcheck = memberRepository.findByEmail(member.getEmail()).orElse(null);

        String[] fields = {"email", "name", "password"};
        //defaultErrorAdd
        for (String field : fields) {
            if(bindingResult.hasFieldErrors(field)) {
                StringBuilder errorMessage = new StringBuilder("");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors(field);
                for (FieldError fieldError : fieldErrors) {
                    errorMessage.append(fieldError.getDefaultMessage() + "<br>");
                }
                response.add(new ValidCheckResponse(false, field, errorMessage.toString()));
            }
        }

        //custom
        if (!bindingResult.hasFieldErrors("email")) {
            if (emailcheck != null) {
                response.add(new ValidCheckResponse(false, "email", "중복되는 이메일이 있어요"));
            } else {
                response.add(new ValidCheckResponse(true, "email", "멋진 이메일이에요"));
            }
        }
        if (!bindingResult.hasFieldErrors("name")) {
            response.add(new ValidCheckResponse(true, "name", "멋진 이름이에요"));
        }
        if (!bindingResult.hasFieldErrors("password")) {
            response.add(new ValidCheckResponse(true, "password", "멋진 비밀번호에요"));
        }

        return response;
    }

    public List editValidCheck(Member member, BindingResult bindingResult){

        List<ValidCheckResponse> response = new ArrayList<>();
        Member emailcheck = memberRepository.findByEmail(member.getEmail()).orElse(null);

        String[] fields = {"email", "name", "password"};
        //defaultErrorAdd
        for (String field : fields) {
            if(bindingResult.hasFieldErrors(field)) {
                StringBuilder errorMessage = new StringBuilder("");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors(field);
                for (FieldError fieldError : fieldErrors) {
                    errorMessage.append(fieldError.getDefaultMessage() + "<br>");
                }
                response.add(new ValidCheckResponse(false, field, errorMessage.toString()));
            }
        }

        //custom
        if (!bindingResult.hasFieldErrors("name")) {
            response.add(new ValidCheckResponse(true, "name", "멋진 이름이에요"));
        }
        if(member.getPassword() != null){
            if (!bindingResult.hasFieldErrors("password")) {
                if(emailcheck.getPassword().equals(member.getPassword())){
                    response.add(new ValidCheckResponse(false, "password", "현재 비밀번호와 같습니다"));
                }else{
                    response.add(new ValidCheckResponse(true, "password", "멋진 비밀번호에요"));
                }
            }
        }
        if(!emailcheck.getPassword().equals(member.getOldPassword())){
            response.add(new ValidCheckResponse(false, "oldPassword", "비밀번호가 일치하지 않습니다"));
        }else{
            response.add(new ValidCheckResponse(true, "oldPassword", "비밀번호가 일치합니다"));
        }

        return response;
    }
}
