const db = require("../puesto");
const Member = db.sociosstarwars;
const Op = db.Sequelize.Op;

// Create and Save a new Member
// req --> request (contains the body)
exports.create = (req, res) => {
  // Validate request
  if (!req.body.nombre || !req.body.apellido || !req.body.puesto) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
    return;
  }

  // Create a Member
  const member = {
    nombre: req.body.nombre,
    apellido: req.body.apellido,
    puesto: req.body.puesto
  };

  // Save Member in the database
  Member.create(member)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the member."
      });
    });
};

// Retrieve all Member from the database.
exports.findAll = (req, res) => {
  
    Member.findAll()
      .then(data => {
        res.send(data);
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving member."
        });
      });
};

// Find a single Member with an id
exports.findOne = (req, res) => {
  let id = req.params.id;
  Member.findByPk(id)
    .then(data => {
      console.log("estos son los datos")
      console.log(data);
      if(!data){
        res.status(400).send({
          message:
            "No Member found with that id"
        })
      }
      res.send(data);
      return;
    })
    .catch(err => {
      console.log(err.message);
      console.log("hola");
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving member with id"
      });
      return;
    });
};

// Update a Member by the id in the request
exports.update = (req, res) => {
  const id = req.params.id;

  Member.update(req.body, {
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Member was updated successfully."
        });
      } else {
        res.send({
          message: `Cannot update Member with id=${id}. Maybe Member was not found or req.body is empty!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Error updating Member with id=" + id
      });
    });
};

// Delete a Tutorial with the specified id in the request
exports.delete = (req, res) => {
  const id = req.params.id;

  Member.destroy({
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Member was deleted successfully!"
        });
      } else {
        res.send({
          message: `Cannot delete Member with id=${id}. Maybe Member was not found!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Could not delete Member with id=" + id
      });
    });
};

// Delete all Member from the database.
exports.deleteAll = (req, res) => {
    Member.destroy({
    where: {},
    truncate: false
  })
    .then(nums => {
      res.send({ message: `${nums} Member were deleted successfully!` });
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while removing all Member."
      });
    });
};