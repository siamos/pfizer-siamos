package Service;

import Interface.CarbInterface;
import model.Carb;
import repository.CarbRepository;
import representation.CarbRepresentation;

import java.util.ArrayList;
import java.util.List;

public class CarbServiceImpl implements CarbInterface {

    private CarbRepository carbRepository;

    public CarbServiceImpl(CarbRepository carbRepository) {
        this.carbRepository = carbRepository;
    }

    @Override
    public CarbRepresentation getCarb(long id) {
        Carb carb = this.carbRepository.read(id);
        return new CarbRepresentation(carb);
    }

    @Override
    public List<Carb> findAllCarbs(int offset, int limit) {

        return this.carbRepository.findAll(offset, limit);
    }

    public List<CarbRepresentation> createCarbRepresentationList(List<Carb> carbs)
    {
        List<CarbRepresentation> carbRepresentationList = new ArrayList<>();
        for (Carb p : carbs)
            carbRepresentationList.add(new CarbRepresentation(p));

        return carbRepresentationList;
    }
}
