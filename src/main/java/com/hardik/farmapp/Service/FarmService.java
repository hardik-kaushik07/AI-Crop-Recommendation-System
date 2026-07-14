package com.hardik.farmapp.Service;

import com.hardik.farmapp.Entity.FarmAnalysis;
import com.hardik.farmapp.Entity.Users;
import com.hardik.farmapp.Repository.FarmAnalysisRepository;
import com.hardik.farmapp.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FarmService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FarmAnalysisRepository farmAnalysisRepository;


    public List<FarmAnalysis> getHistory(Authentication authentication, int pageNumber, int pageSize) {

        String email = authentication.getName();

        Users user = usersRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Sorry the user is not Found");
        }


        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return farmAnalysisRepository.findByUser(user, pageable).getContent();
    }

    public FarmAnalysis getHistoryById(Long id, Authentication authentication) {

        String email = authentication.getName();

        Users user = usersRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Sorry the user is not Found");
        }


        return farmAnalysisRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("History not found"));
    }


    @Transactional
    public String deleteAllHistory(Authentication authentication) {

        String email = authentication.getName();

        Users user = usersRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Sorry the user is not Found");
        }

        farmAnalysisRepository.deleteByUser(user);

        return"All history deleted Successfully.";
    }

    public String deleteHistoryById(Long id, Authentication authentication) {

        String email = authentication.getName();

        Users user = usersRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("Sorry the user is not Found");
        }

        FarmAnalysis analysis = farmAnalysisRepository.findById(id).
                orElseThrow(()->  new RuntimeException("No analysis found"));

        if(!analysis.getUser().getId().equals(user.getId())){
                throw new RuntimeException("Sorry you are not allow to delete");
        }

        farmAnalysisRepository.delete(analysis);

        return "History deleted Successfully";

    }
}
