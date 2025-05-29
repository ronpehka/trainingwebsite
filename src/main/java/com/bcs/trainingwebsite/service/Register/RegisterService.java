package com.bcs.trainingwebsite.service.Register;

import com.bcs.trainingwebsite.Status;
import com.bcs.trainingwebsite.infrastructure.exception.DataNotFoundException;
import com.bcs.trainingwebsite.infrastructure.exception.ForeignKeyNotFoundException;
import com.bcs.trainingwebsite.persistance.register.Register;
import com.bcs.trainingwebsite.persistance.register.RegisterRepository;
import com.bcs.trainingwebsite.persistance.training.Training;
import com.bcs.trainingwebsite.persistance.training.TrainingRepository;
import com.bcs.trainingwebsite.persistance.user.User;
import com.bcs.trainingwebsite.persistance.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor

public class RegisterService {

    private final RegisterRepository registerRepository;
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    public void registerUserToTraining(Integer trainingId, Integer userId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("trainingId", trainingId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("userId", userId));

        Register register = new Register();
        register.setTraining(training);
        register.setUser(user);
        register.setStatus(Status.ACTIVE.getCode());
        register.setDate(LocalDate.now());

        registerRepository.save(register);
    }

    public void unregisterUserFromTraining(Integer trainingId, Integer userId) {
        Register register = registerRepository.findByUserIdAndTrainingId(trainingId, userId)
                .orElseThrow(() -> new DataNotFoundException("No registration found for userId: " + userId + " and trainingId: " + trainingId, 404));
        registerRepository.delete(register);

    }
}
