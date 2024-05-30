package ch.unisg.factory.core.services.camunda07;

import ch.unisg.factory.core.entities.Factory;
import ch.unisg.factory.core.entities.TransferOfGoodsRequest;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("DecreaseInventoryLevelProcess")
public class DecreaseInventoryLevelProcess implements JavaDelegate {
    @Override
    public void execute(DelegateExecution context) throws Exception {
        Factory factory = Factory.getFactory();
        TransferOfGoodsRequest request = factory.getTransferOfGoodsRequestById(
                (String)context.getVariable("transferOfGoodsRequestId")
        );

        var goodsAmountToDecrease = request.getGoodsAmount().value();
        factory.decreaseInventoryLevel(goodsAmountToDecrease);

        System.out.println("Decreasing inventory level of { " + goodsAmountToDecrease + " }");
    }
}
