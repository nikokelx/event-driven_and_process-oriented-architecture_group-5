package ch.unisg.factory.core.services;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.entities.TransferOfGoodsRequest;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelCommand;
import ch.unisg.factory.core.ports.in.UpdateMachineFillLevelUseCase;
import ch.unisg.factory.core.ports.out.CollectMachineFillLevelPort;
import ch.unisg.factory.core.ports.out.PublishFactoryInventoryLevelPort;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UpdateMachineFillLevel implements UpdateMachineFillLevelUseCase {

    @Autowired
    private RuntimeService runtimeService;

    private final CollectMachineFillLevelPort collectMachineFillLevelPort;

    private final PublishFactoryInventoryLevelPort publishFactoryInventoryLevelPort;

    @Override
    public void updateMachineFillLevel(UpdateMachineFillLevelCommand command) {
        Factory factory = Factory.getFactory();

        if (command.getMachineFillLevel().getValue() >= 10) {

            // Get goods from the machine
            int fillLevel = collectMachineFillLevelPort.collectMachineFillLevel();
            factory.increaseInventoryLevel(fillLevel);

            // Emit event
            publishFactoryInventoryLevelPort.publishFactoryInventoryLevel(factory.getInventoryLevel());

            if (factory.getInventoryLevel().value() >= 30 && factory.getInventoryLevel().value() <= 40) {
                var goodsAmountToTransfer = new TransferOfGoodsRequest.GoodsAmount(factory.getInventoryLevel().value());
                TransferOfGoodsRequest transferRequest = new TransferOfGoodsRequest(goodsAmountToTransfer);
                factory.addNewTransferRequestToList(transferRequest);

                Map<String, Object> variables = new HashMap<>();
                variables.put("transferOfGoodsRequestId", transferRequest.getId().value());
                runtimeService.startProcessInstanceByKey(
                        "TransferGoodsRequestProcess",
                        transferRequest.getId().value(),
                        variables
                );

                System.out.println("Start TransferGoodsRequest Process");
            }
        }
    }
}
