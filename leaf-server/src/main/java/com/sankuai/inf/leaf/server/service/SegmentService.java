package com.sankuai.inf.leaf.server.service;

import com.sankuai.inf.leaf.IDGen;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.sankuai.inf.leaf.segment.dao.IDAllocDao;
import com.sankuai.inf.leaf.segment.dao.impl.IDAllocDaoImpl;
import com.sankuai.inf.leaf.server.exception.InitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service("SegmentService")
public class SegmentService {
    private Logger logger = LoggerFactory.getLogger(SegmentService.class);

    private IDGen idGen;

    @Autowired
    public SegmentService(DataSource dataSource)throws InitException {
        IDAllocDao dao = new IDAllocDaoImpl(dataSource);
        idGen = new SegmentIDGenImpl();
        ((SegmentIDGenImpl) idGen).setDao(dao);
        if (idGen.init()) {
            logger.info("Segment Service Init Successfully");
        } else {
            throw new InitException("Segment Service Init Fail");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}
