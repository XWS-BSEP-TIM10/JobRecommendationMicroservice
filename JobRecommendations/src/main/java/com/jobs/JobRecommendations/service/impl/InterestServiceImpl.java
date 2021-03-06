package com.jobs.JobRecommendations.service.impl;

import com.jobs.JobRecommendations.model.Interest;
import com.jobs.JobRecommendations.model.User;
import com.jobs.JobRecommendations.repository.InterestRepository;
import com.jobs.JobRecommendations.service.InterestService;
import com.jobs.JobRecommendations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;
    private final UserService userService;

    @Autowired
    public InterestServiceImpl(InterestRepository interestRepository, UserService userService) {
        this.interestRepository = interestRepository;
        this.userService = userService;
    }

    @Override
    public Interest save(Interest interest) {
        return interestRepository.save(interest);
    }

    @Override
    public Interest addInterest(Interest interest, User user) {
        User newUser = findUser(user);
        Interest newInterest = saveInterest(interest);
        newUser.getInterests().add(newInterest);
        userService.save(newUser);
        return newInterest;
    }

    @Override
    public Interest findByDescription(String description) {
        if(interestRepository.findByDescription(description).isPresent()){
            return interestRepository.findByDescription(description).get();
        }
        return null;
    }

    private Interest saveInterest(Interest interest){
        if(interestRepository.findByDescription(interest.getDescription()).isPresent()){
            return interestRepository.findByDescription(interest.getDescription()).get();
        }
        return interestRepository.save(interest);
    }

    private User findUser(User user){
        User existingUser = userService.findById(user.getId());
        if(existingUser != null){
            return existingUser;
        }
        return user;
    }

}
