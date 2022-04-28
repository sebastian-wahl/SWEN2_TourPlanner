package at.fhtw.swen2_tourplanner.swen2_tourplanner.controller;

import at.fhtw.swen2_tourplanner.swen2_tourplanner.viewmodel.ViewModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseController<T extends ViewModel> {
    private T viewModel;
}
