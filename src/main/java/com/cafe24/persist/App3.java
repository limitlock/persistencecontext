package com.cafe24.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.cafe24.persist.domain.Member;

public class App3 {
	public static void main(String[] args) {

		// 1. Entity Manager Factory 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persitencecontext"); // db

		// 2. Entity Manager 생성
		EntityManager em = emf.createEntityManager();

		// 3.Get TX
		EntityTransaction tx = em.getTransaction();

		// 4. TX Begins
		// 엔티티 매니저는 데이터 변경 시 트랜잭션을 시작하여야 한다.
		tx.begin(); // [트랜잭션] 시작

		// 5. Business Logic
		try {
			Member memberC = new Member();
			memberC.setId("memberC");
			memberC.setName("회원C");
			em.persist(memberC);

			memberC.setName("둘리");

			// JPA는 update()메소드가 존재하지 않는다.
			// 변경감지(Dirty Checking)를 이용하여 1차 캐시에
			// 저장된 데이터가 기존의 값과 변경되었는지를 확인하고
			// 변경되었다면 sql update문을 실행한다.

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		// 6. TX Commit
		// 6-1. 커밋하는 순간에 변경감지(Dirty Checking)을 한다.
		// 6-2. 변경이 감지되면 update sql를 저장소에 저장한다.
		// 6-3. flush()
		tx.commit(); // [트랜잭션] 커밋

		// 7. Entity Manager 종료
		em.clear();

		// 8. Entity Manager Factory 종료
		emf.close();

	}

}
