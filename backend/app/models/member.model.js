module.exports = (sequelize, Sequelize) => {
    const Member = sequelize.define("member", {
      nombre: {
        type: Sequelize.STRING
      },
      apellido: {
        type: Sequelize.STRING
      },
      puesto: {
        type: Sequelize.STRING
      }
    }, { timestamps: false});
  
    return Member;
  };