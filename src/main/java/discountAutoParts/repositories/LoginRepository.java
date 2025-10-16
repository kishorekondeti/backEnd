package discountAutoParts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import discountAutoParts.models.UsersModel;


public interface LoginRepository extends JpaRepository<UsersModel, Long>{

	UsersModel findByEmail(String email);

}
