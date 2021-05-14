package Interface;

import model.Carb;
import representation.CarbRepresentation;

import java.util.List;

public interface CarbInterface {
    CarbRepresentation getCarb(long id);
    List<Carb> findAllCarbs(int offset, int limit);
}
