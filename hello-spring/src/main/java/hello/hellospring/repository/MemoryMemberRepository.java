package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import java.util.*;
/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // 값이 null이어도 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store의 값을 List로 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // 루프를 돌면서 파라미터로 넘어온 name과 같은 값을 Map에서 찾아 반환
    }

    public void clearStore() {
        store.clear();
    }
}
