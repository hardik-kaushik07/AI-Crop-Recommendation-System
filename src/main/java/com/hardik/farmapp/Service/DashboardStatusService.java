package com.hardik.farmapp.Service;

import com.hardik.farmapp.DTO.DashboardStatus;
import com.hardik.farmapp.Entity.Users;
import com.hardik.farmapp.Repository.ChatRepository;
import com.hardik.farmapp.Repository.DocumentMetaDataRepository;
import com.hardik.farmapp.Repository.FarmAnalysisRepository;
import com.hardik.farmapp.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardStatusService {

    private final UsersRepository usersRepository;

    private final ChatRepository chatRepository;

    private final FarmAnalysisRepository farmAnalysisRepository;

    private final DocumentMetaDataRepository documentRepository;

    public DashboardStatus getDashboardStats(Authentication authentication) {

        Users user =
                usersRepository.findByEmail(authentication.getName());

        if (user == null) {

            throw new UsernameNotFoundException("User not found");

        }

        long chatCount =
                chatRepository.countByUser(user);

        long analysisCount =
                farmAnalysisRepository.countByUser(user);

        long pdfCount =
                documentRepository.countByUploadedByAndFileCategory(
                        user,
                        "DOCUMENT"
                );

        long imageCount =
                documentRepository.countByUploadedByAndFileCategory(
                        user,
                        "IMAGE"
                );

        return new DashboardStatus(
                chatCount,
                analysisCount,
                pdfCount,
                imageCount
        );
    }
}
