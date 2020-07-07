import com.ppdai.das.client.DasClient;
import com.ppdai.das.client.DasClientFactory;

import java.sql.SQLException;

/**
 * @description: https://github.com/ppdaicorp/das/wiki/DasClient-Hints%E4%BD%BF%E7%94%A8%E6%89%8B%E5%86%8C
 * @author: shitou<wanglei @ ichangtou.com>
 * @created: 2020/06/29 11:11
 */
public class Test {
    public static void main(String[] args) throws SQLException {
//        DefaultClientConfigLoader
//        AdvancedModStrategy
        DasClient dasClient = DasClientFactory.getClient("das_shardtest01");
    }
}