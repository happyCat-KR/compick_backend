package kr.gg.compick.board.dao;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.gg.compick.board.dto.BoardViewDTO;

import java.util.List;

@Repository
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BoardViewDTO> findBoardsByMatchtag(String matchtagName) {
        String sql = """
                    SELECT
                        t.Board_idx AS boardIdx,
                        u.profile_image AS profileImage,
                        GROUP_CONCAT(DISTINCT h.matchtag_name) AS matchtagName,
                        u.user_idx AS userIdx,
                        u.user_id AS userId,
                        t.content AS content,
                        GROUP_CONCAT(DISTINCT m.file_url) AS fileUrls,
                        COUNT(DISTINCT tl.user_idx) AS likeCount,
                        (SELECT COUNT(*) FROM board c WHERE c.parent_idx = t.board_idx) AS commentCount
                    FROM USER u
                    LEFT JOIN board t ON u.user_idx = t.user_idx
                    LEFT JOIN media m ON t.board_idx = m.board_idx
                    LEFT JOIN board_like tl ON t.board_idx = tl.board_idx AND tl.del_check = 0
                    LEFT JOIN board_matchtag th ON t.board_idx = th.board_idx
                    LEFT JOIN matchtag h ON th.matchtag_idx = h.matchtag_idx
                    WHERE t.board_idx IN (
                        SELECT board_idx FROM board_matchtag WHERE matchtag_idx = (
                            SELECT matchtag_idx FROM matchtag WHERE matchtag_name = :matchtagName
                        )
                    )
                    AND t.parent_idx IS NULL AND t.del_check = 0
                    GROUP BY t.board_idx, u.profile_image, u.user_id, t.content
                    ORDER BY likeCount DESC
                """;

        return em.createNativeQuery(sql, "BoardViewMapping")
                .setParameter("matchtagName", matchtagName)
                .getResultList();
    }

    @Override
    public List<BoardViewDTO> findBoardsByContentKeyword(String keyword) {
        String sql = """
                    SELECT
                        t.board_idx AS boardIdx,
                        u.profile_image AS profileImage,
                        GROUP_CONCAT(DISTINCT h.matchtag_name) AS matchtagName,
                        u.user_idx AS userIdx,
                        u.user_id AS userId,
                        t.content AS content,
                        GROUP_CONCAT(DISTINCT m.file_url) AS fileUrls,
                        COUNT(DISTINCT tl.user_idx) AS likeCount,
                        (SELECT COUNT(*) FROM board c WHERE c.parent_idx = t.board_idx) AS commentCount
                    FROM USER u
                    LEFT JOIN board t ON u.user_idx = t.user_idx
                    LEFT JOIN media m ON t.board_idx = m.board_idx
                    LEFT JOIN board_like tl ON t.board_idx = tl.board_idx AND tl.del_check = 0
                    LEFT JOIN board_matchtag th ON t.board_idx = th.board_idx
                    LEFT JOIN matchtag h ON th.matchtag_idx = h.matchtag_idx
                    WHERE t.board_idx IN (
                        SELECT board_idx FROM board WHERE content LIKE CONCAT('%', :keyword, '%')
                    )
                    AND t.parent_idx IS NULL AND t.del_check = 0
                    GROUP BY t.board_idx, u.profile_image, u.user_id, t.content
                    ORDER BY likeCount DESC
                """;

        return em.createNativeQuery(sql, "BoardViewMapping")
                .setParameter("keyword", keyword)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BoardViewDTO> findTopBoardsByUser(Long userIdx) {
        return em.createNativeQuery("""
                    SELECT
                        t.board_idx AS boardIdx,
                        u.profile_image AS profileImage,
                        GROUP_CONCAT(DISTINCT h.matchtag_name) AS matchtagName,
                        u.user_idx AS userIdx,
                        u.user_id AS userId,
                        t.content AS content,
                        GROUP_CONCAT(DISTINCT m.file_url) AS fileUrls,
                        COUNT(DISTINCT tl.user_idx) AS likeCount,
                        (SELECT COUNT(*) FROM board c WHERE c.parent_idx = t.board_idx) AS commentCount
                    FROM USER u
                    LEFT JOIN board t ON u.user_idx = t.user_idx
                    LEFT JOIN media m ON t.board_idx = m.board_idx
                    LEFT JOIN board_like tl ON t.board_idx = tl.board_idx AND tl.del_check = 0
                    LEFT JOIN board_matchtag th ON t.board_idx = th.board_idx
                    LEFT JOIN matchtag h ON th.matchtag_idx = h.matchtag_idx
                    WHERE t.board_idx IN (
                        SELECT th.board_idx
                        FROM board_matchtag th
                        LEFT JOIN board_like tl ON th.board_idx = tl.board_idx AND tl.del_check = 0
                        WHERE th.matchtag_idx IN (
                            SELECT matchtag_idx FROM (
                                SELECT ua.matchtag_idx
                                FROM user_action ua
                                LEFT JOIN action_type at ON ua.action_type = at.type_code
                                WHERE ua.user_idx = :userIdx
                                GROUP BY ua.matchtag_idx
                                ORDER BY SUM(at.score_value) DESC
                                LIMIT 2
                            ) AS limited_matchtags
                        )
                        GROUP BY th.board_idx
                    ) AND t.parent_idx IS NULL AND t.del_check = 0
                    GROUP BY t.board_idx, u.profile_image, u.user_id, t.content
                    ORDER BY likeCount DESC
                """, "BoardViewMapping")
                .setParameter("userIdx", userIdx)
                .getResultList();
    }

    @Override
    public List<BoardViewDTO> findFollowingBoards(Long userIdx) {
        return em.createNativeQuery("""
                    SELECT
                        t.board_idx AS boardIdx,
                        u.profile_image AS profileImage,
                        GROUP_CONCAT(DISTINCT h.matchtag_name) AS matchtagName,
                        u.user_idx AS userIdx,
                        u.user_id AS userId,
                        t.content AS content,
                        GROUP_CONCAT(DISTINCT m.file_url) AS fileUrls,
                        COUNT(DISTINCT tl.user_idx) AS likeCount,
                        (SELECT COUNT(*) FROM board c WHERE c.parent_idx = t.board_idx) AS commentCount
                    FROM USER u
                    LEFT JOIN board t ON u.user_idx = t.user_idx
                    LEFT JOIN media m ON t.board_idx = m.board_idx
                    LEFT JOIN board_like tl ON t.board_idx = tl.board_idx AND tl.del_check = 0
                    LEFT JOIN board_matchtag th ON t.board_idx = th.board_idx
                    LEFT JOIN matchtag h ON th.matchtag_idx = h.matchtag_idx
                    WHERE t.board_idx IN (
                        SELECT board_idx
                        FROM board
                        WHERE user_idx IN (
                            SELECT following_id
                            FROM follow
                            WHERE follower_id = :userIdx AND del_check = 0
                        )
                    ) AND t.parent_idx IS NULL AND t.del_check = 0
                    GROUP BY t.board_idx, u.profile_image, u.user_id, t.content
                    ORDER BY likeCount DESC
                """, "BoardViewMapping")
                .setParameter("userIdx", userIdx)
                .getResultList();
    }


    @Override
    public BoardViewDTO findDetailBoard(Long boardIdx) {
        String sql = """
        WITH RECURSIVE reply_tree AS (
            SELECT board_idx, parent_idx
            FROM board
            WHERE parent_idx = :boardIdx
            UNION ALL
            SELECT t.board_idx, t.parent_idx
            FROM board t
            JOIN reply_tree rt ON t.parent_idx = rt.board_idx
        )
        SELECT 
            t.board_idx AS boardIdx,
            u.profile_image AS profileImage,
            GROUP_CONCAT(DISTINCT h.matchtag_name) AS matchtagName,
            u.user_idx AS userIdx,
            u.user_id AS userId,
            t.content AS content,
            GROUP_CONCAT(DISTINCT m.file_url) AS fileUrls,
            COUNT(DISTINCT tl.user_idx) AS likeCount,
            (SELECT COUNT(*) FROM reply_tree) AS commentCount
        FROM USER u
        LEFT JOIN board t ON u.user_idx = t.user_idx
        LEFT JOIN media m ON t.board_idx = m.board_idx
        LEFT JOIN board_like tl ON t.board_idx = tl.board_idx AND tl.del_check = 0
        LEFT JOIN board_matchtag th ON t.board_idx = th.board_idx
        LEFT JOIN matchtag h ON th.matchtag_idx = h.matchtag_idx
        WHERE t.board_idx = :boardIdx AND t.del_check = 0
        GROUP BY t.board_idx, u.profile_image, u.user_id, t.content
        ORDER BY likeCount DESC
    """;
        return (BoardViewDTO) em.createNativeQuery(sql, "BoardViewMapping")
                .setParameter("boardIdx", boardIdx)
                .getSingleResult();
    }


     @Override
    public List<BoardViewDTO> findRepliesOfBoard(Long parentIdx) {
        String sql = """
        SELECT 
            t.board_idx AS boardIdx,
            u.profile_image AS profileImage,
            GROUP_CONCAT(DISTINCT h.matchtag_name) AS matchtagName,
            u.user_idx AS userIdx,
            u.user_id AS userId,
            t.content AS content,
            GROUP_CONCAT(DISTINCT m.file_url) AS fileUrls,
            COUNT(DISTINCT tl.user_idx) AS likeCount,
            (SELECT COUNT(*) FROM board c WHERE c.parent_idx = t.board_idx) AS commentCount
        FROM USER u
        LEFT JOIN board t ON u.user_idx = t.user_idx
        LEFT JOIN media m ON t.board_idx = m.board_idx
        LEFT JOIN board_like tl ON t.board_idx = tl.board_idx AND tl.del_check = 0
        LEFT JOIN board_matchtag th ON t.board_idx = th.board_idx
        LEFT JOIN matchtag h ON th.matchtag_idx = h.matchtag_idx
        WHERE t.board_idx IN (
            SELECT board_idx FROM board WHERE parent_idx = :parentIdx
        ) AND t.del_check = 0
        GROUP BY t.board_idx, u.profile_image, u.user_id, t.content
        ORDER BY likeCount DESC
    """;
        return em.createNativeQuery(sql, "BoardViewMapping")
                .setParameter("parentIdx", parentIdx)
                .getResultList();
    }


}