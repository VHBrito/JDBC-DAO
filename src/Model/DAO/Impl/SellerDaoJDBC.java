package Model.DAO.Impl;

import DB.DBException;
import Model.DAO.SellerDAO;
import Model.Entities.Department;
import Model.Entities.Seller;
import DB.DB;

import java.sql.*;
import java.util.List;

public class SellerDaoJDBC implements SellerDAO {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*, department.department_name as DepName "
                            + "FROM Seller INNER JOIN department "
                            + "ON seller.department_id = department.department_id "
                            + "WHERE seller.seller_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Department department = instantiateDepartment(resultSet);
                Seller seller = instantiateSeller(resultSet, department);
                return seller;
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(preparedStatement);
        }
        return null;
    }

    private Department instantiateDepartment(ResultSet resultSet) {
        try {
            return new Department(resultSet.getInt("department_id"), resultSet.getString("DepName"));
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) {
        try {
            return new Seller(
                    resultSet.getInt("seller_id"),
                    resultSet.getString("seller_name"),
                    resultSet.getString("email"),
                    resultSet.getDate("birthdate"),
                    resultSet.getDouble("basesalary"), department);
        }
        catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
