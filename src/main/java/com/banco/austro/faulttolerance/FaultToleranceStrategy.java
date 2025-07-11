package com.banco.austro.faulttolerance;

import com.banco.austro.dto.MoveResponse;

public interface FaultToleranceStrategy {
    MoveResponse execute() throws Exception;
}