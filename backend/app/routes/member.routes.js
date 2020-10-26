module.exports = app => {
    const member = require("../controllers/member.controller.js");
  
    var router = require("express").Router();
  
    // Create a new Bicycle
    router.post("/", Member.create);
  
    // Retrieve all Bicycles
    router.get("/", Member.findAll);
  
    // Retrieve a single bicycle with id
    router.get("/:id", Member.findOne);
  
    // Update a bicycle with id
    router.put("/:id", Member.update);
  
    // Delete a bicycle with id
    router.delete("/:id", Member.delete);
  
    // Delete all bicycles
    router.delete("/", Member.deleteAll);
  
    app.use('/api/member', router);
  };