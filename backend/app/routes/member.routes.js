module.exports = app => {
    const member = require("../controllers/member.controller.js");
  
    var router = require("express").Router();
  
    // Create a new Bicycle
    router.post("/", member.create);
  
    // Retrieve all Bicycles
    router.get("/", member.findAll);
  
    // Retrieve a single bicycle with id
    router.get("/:id", member.findOne);
  
    // Update a bicycle with id
    router.put("/:id", member.update);
  
    // Delete a bicycle with id
    router.delete("/:id", member.delete);
  
    // Delete all bicycles
    router.delete("/", member.deleteAll);
  
    app.use('/api/members', router);
  };