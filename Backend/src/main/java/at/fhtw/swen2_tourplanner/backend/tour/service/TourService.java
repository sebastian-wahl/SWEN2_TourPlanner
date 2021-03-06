package at.fhtw.swen2_tourplanner.backend.tour.service;

import at.fhtw.swen2_tourplanner.backend.tour.dto.TourDTO;
import at.fhtw.swen2_tourplanner.backend.util.BusinessException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TourService {
    TourDTO createTour(TourDTO tour) throws BusinessException;

    List<TourDTO> createTours(List<TourDTO> tourList) throws BusinessException;

    TourDTO updateTour(TourDTO tour) throws BusinessException;

    //Service Method declarations
    TourDTO getTour(UUID id) throws BusinessException;

    boolean deleteTour(UUID id) throws IOException;

    List<TourDTO> getAllTours();
}
