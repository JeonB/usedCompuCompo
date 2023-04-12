package thwjd.usedbook.repository;

import org.springframework.stereotype.Repository;
import thwjd.usedbook.entity.Member;

import java.util.*;

@Repository
public class MemberRepository {
    private Map<Long, Member> store = new HashMap<>();
    private Long sequence = 0L;

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return store.get(member.getId());
    }

    public Member findById(Long findid){
        return store.get(findid);
    }

    public Optional<Member> findByEmail(String email){
        //optional이어야 loginservice에서 filter사용 가능
        return findAll().stream()
                .filter(m -> m.getEmail().equals(email))
                .findFirst();
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByEmailAndName(String email, String name){
        return findByEmail(email)
                .filter(m -> m.getName().equals(name));
    }
}
